package com.winnguyen1905.payment.persistance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Records customer delivery confirmations and auto-confirmation logic.
 * Tracks the status of delivery confirmations which are used to trigger the release of funds from escrow.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery_confirmations", indexes = {
    @Index(name = "idx_delivery_order_id", columnList = "order_id"),
    @Index(name = "idx_delivery_customer_id", columnList = "customer_id"),
    @Index(name = "idx_delivery_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_delivery_status", columnList = "status"),
    @Index(name = "idx_delivery_reported_date", columnList = "delivery_reported_date")
})
public class DeliveryConfirmation extends BaseAuditEntity {

    @NotNull
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @NotNull
    @Column(name = "shipping_provider_id", nullable = false)
    private Long shippingProviderId;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DeliveryStatus status;

    @NotNull
    @Column(name = "delivery_reported_date", nullable = false)
    private LocalDateTime deliveryReportedDate;

    @Column(name = "confirmation_window_days", nullable = false)
    private Integer confirmationWindowDays;

    @Column(name = "auto_confirm_date", nullable = false)
    private LocalDateTime autoConfirmDate;

    @Column(name = "customer_confirmed_date")
    private LocalDateTime customerConfirmedDate;

    @Column(name = "confirmation_method")
    private String confirmationMethod;

    @Column(name = "confirmation_notes", columnDefinition = "TEXT")
    private String confirmationNotes;

    @Column(name = "proof_of_delivery", columnDefinition = "TEXT")
    private String proofOfDelivery;

    @Column(name = "delivery_exception_reason", columnDefinition = "TEXT")
    private String deliveryExceptionReason;

    /**
     * Delivery status enumeration
     */
    public enum DeliveryStatus {
        REPORTED_DELIVERED,   // Shipping provider reported delivery
        CUSTOMER_CONFIRMED,   // Customer confirmed receipt
        AUTO_CONFIRMED,       // System auto-confirmed after window expiration
        DISPUTED,             // Customer disputed delivery
        REJECTED              // Customer reported non-delivery
    }
} 
