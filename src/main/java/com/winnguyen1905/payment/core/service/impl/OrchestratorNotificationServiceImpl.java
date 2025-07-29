package com.winnguyen1905.payment.core.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.winnguyen1905.payment.core.feign.OrchestratorServiceClient;
import com.winnguyen1905.payment.core.model.request.PaymentStatusUpdateRequest;
import com.winnguyen1905.payment.core.service.OrchestratorNotificationService;
import com.winnguyen1905.payment.persistance.entity.EPayment;
import com.winnguyen1905.payment.persistance.entity.PaymentTransaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrchestratorNotificationServiceImpl implements OrchestratorNotificationService {

    private final OrchestratorServiceClient orchestratorServiceClient;

    @Override
    public void notifyPaymentStatusChange(EPayment payment) {
        try {
            PaymentStatusUpdateRequest request = PaymentStatusUpdateRequest.builder()
                    .orderId(convertLongToUUID(payment.getOrderId()))
                    .paymentId(payment.getId())
                    .paymentStatus(mapPaymentStatus(payment.getStatus()))
                    .amount(payment.getAmount())
                    .currency(payment.getCurrency())
                    .transactionId(payment.getTransactionId())
                    .paymentMethod(payment.getPaymentType() != null ? payment.getPaymentType().toString() : null)
                    .failureReason(payment.getFailureReason())
                    .gatewayResponse(payment.getGatewayResponse())
                    .processedAt(payment.getUpdatedAt() != null ? payment.getUpdatedAt() : Instant.now())
                    .metadata(payment.getMetadata())
                    .build();

            orchestratorServiceClient.updatePaymentStatus(request);
            log.info("Successfully notified orchestrator of payment status change for paymentId: {}, orderId: {}, status: {}", 
                    payment.getId(), payment.getOrderId(), payment.getStatus());

        } catch (Exception e) {
            log.error("Failed to notify orchestrator of payment status change for paymentId: {}, orderId: {}", 
                    payment.getId(), payment.getOrderId(), e);
            // In a production system, you might want to implement retry logic or dead letter queue
        }
    }

    @Override
    public void notifyTransactionStatusChange(PaymentTransaction transaction) {
        try {
            PaymentStatusUpdateRequest request = PaymentStatusUpdateRequest.builder()
                    .orderId(convertLongToUUID(transaction.getOrderId()))
                    .paymentId(transaction.getId()) // Using transaction ID as payment ID
                    .paymentStatus(mapTransactionStatus(transaction.getStatus()))
                    .amount(transaction.getAmount())
                    .currency(transaction.getCurrency())
                    .transactionId(transaction.getTransactionId())
                    .paymentMethod(transaction.getPaymentMethod() != null ? transaction.getPaymentMethod().toString() : null)
                    .failureReason(transaction.getFailureReason())
                    .processedAt(transaction.getUpdatedAt() != null ? 
                        transaction.getUpdatedAt().atZone(java.time.ZoneOffset.UTC).toInstant() : 
                        Instant.now())
                    .build();

            orchestratorServiceClient.updatePaymentStatus(request);
            log.info("Successfully notified orchestrator of transaction status change for transactionId: {}, orderId: {}, status: {}", 
                    transaction.getId(), transaction.getOrderId(), transaction.getStatus());

        } catch (Exception e) {
            log.error("Failed to notify orchestrator of transaction status change for transactionId: {}, orderId: {}", 
                    transaction.getId(), transaction.getOrderId(), e);
        }
    }

    @Override
    public void notifyWebhookProcessed(UUID paymentId, UUID orderId, String status, 
                                     String transactionId, String failureReason) {
        try {
            PaymentStatusUpdateRequest request = PaymentStatusUpdateRequest.builder()
                    .orderId(orderId)
                    .paymentId(paymentId)
                    .paymentStatus(status)
                    .transactionId(transactionId)
                    .failureReason(failureReason)
                    .processedAt(Instant.now())
                    .build();

            orchestratorServiceClient.updatePaymentStatus(request);
            log.info("Successfully notified orchestrator of webhook processing for paymentId: {}, orderId: {}, status: {}", 
                    paymentId, orderId, status);

        } catch (Exception e) {
            log.error("Failed to notify orchestrator of webhook processing for paymentId: {}, orderId: {}", 
                    paymentId, orderId, e);
        }
    }

    /**
     * Convert Long ID to UUID for compatibility
     */
    private UUID convertLongToUUID(Long id) {
        if (id == null) {
            return null;
        }
        // Simple conversion - in a real system you might want a more sophisticated mapping
        return UUID.fromString(String.format("00000000-0000-0000-0000-%012d", id));
    }

    /**
     * Map internal payment status to orchestrator-compatible status
     */
    private String mapPaymentStatus(EPayment.PaymentStatus paymentStatus) {
        if (paymentStatus == null) {
            return "PENDING";
        }
        
        switch (paymentStatus) {
            case COMPLETED:
            case CAPTURED:
                return "COMPLETED";
            case FAILED:
                return "FAILED";
            case CANCELLED:
                return "CANCELLED";
            case PENDING:
            case PROCESSING:
            case AUTHORIZED:
                return "PENDING";
            case REFUNDED:
            case PARTIALLY_REFUNDED:
                return "REFUNDED";
            default:
                return paymentStatus.toString();
        }
    }

    /**
     * Map transaction status to orchestrator-compatible status
     */
    private String mapTransactionStatus(PaymentTransaction.PaymentStatus transactionStatus) {
        if (transactionStatus == null) {
            return "PENDING";
        }
        
        switch (transactionStatus) {
            case COMPLETED:
                return "COMPLETED";
            case FAILED:
                return "FAILED";
            case CANCELLED:
                return "CANCELLED";
            case PENDING:
            case PROCESSING:
                return "PENDING";
            case REFUNDED:
            case PARTIALLY_REFUNDED:
                return "REFUNDED";
            default:
                return transactionStatus.toString();
        }
    }
} 
