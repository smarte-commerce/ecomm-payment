package com.winnguyen1905.payment.core.service;

import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EPayment;
import com.winnguyen1905.payment.persistance.entity.PaymentTransaction;

public interface OrchestratorNotificationService {

    /**
     * Notify orchestrator of payment status change
     * 
     * @param payment Payment entity
     */
    void notifyPaymentStatusChange(EPayment payment);

    /**
     * Notify orchestrator of payment transaction status change
     * 
     * @param transaction Payment transaction entity
     */
    void notifyTransactionStatusChange(PaymentTransaction transaction);

    /**
     * Notify orchestrator when webhook processing is complete
     * 
     * @param paymentId Payment ID
     * @param orderId Order ID
     * @param status Payment status
     * @param transactionId Transaction ID (optional)
     * @param failureReason Failure reason (optional)
     */
    void notifyWebhookProcessed(UUID paymentId, UUID orderId, String status, 
                               String transactionId, String failureReason);
} 
