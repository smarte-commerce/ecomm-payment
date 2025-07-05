package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Core payment transaction entity that tracks all payment-related information
 * including amount, method, status, and transaction metadata.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_transactions", indexes = {
    @Index(name = "idx_payment_order_id", columnList = "order_id"),
    @Index(name = "idx_payment_customer_id", columnList = "customer_id"),
    @Index(name = "idx_payment_vendor_id", columnList = "vendor_id"),
    @Index(name = "idx_payment_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_payment_status", columnList = "status"),
    @Index(name = "idx_payment_method", columnList = "payment_method"),
    @Index(name = "idx_payment_created_at", columnList = "created_at")
})
public class PaymentTransaction extends BaseAuditEntity {

    @NotNull
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull
    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "transaction_id", nullable = false, unique = true, length = 50)
    private String transactionId;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status;

    @Column(name = "payment_gateway")
    private String paymentGateway;

    @Column(name = "payment_gateway_reference")
    private String paymentGatewayReference;

    @Column(name = "payment_details", columnDefinition = "json")
    private String paymentDetails;

    @Column(name = "failure_reason", length = 255)
    private String failureReason;

    @Column(name = "payment_initiated_at")
    private LocalDateTime paymentInitiatedAt;

    @Column(name = "payment_completed_at")
    private LocalDateTime paymentCompletedAt;

    /**
     * Payment method enumeration
     */
    public enum PaymentMethod {
        ONLINE, 
        CASH_ON_DELIVERY
    }

    /**
     * Payment status enumeration
     */
    public enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        REFUNDED,
        PARTIALLY_REFUNDED,
        CANCELLED
    }
} 
