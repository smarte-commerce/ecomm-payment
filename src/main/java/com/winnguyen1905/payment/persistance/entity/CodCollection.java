package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Tracks cash-on-delivery payment collection status and logistics details.
 * Used for managing payments collected by delivery personnel at the time of product delivery.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cod_collections", indexes = {
    @Index(name = "idx_cod_order_id", columnList = "order_id"),
    @Index(name = "idx_cod_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_cod_status", columnList = "status"),
    @Index(name = "idx_cod_delivery_personnel_id", columnList = "delivery_personnel_id"),
    @Index(name = "idx_cod_collection_date", columnList = "collection_date")
})
public class CodCollection extends BaseAuditEntity {

    @NotNull
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Size(max = 50)
    @Column(name = "transaction_id", nullable = false, length = 50)
    private String transactionId;

    @NotNull
    @Positive
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @NotNull
    @Size(min = 3, max = 3)
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CodStatus status;

    @NotNull
    @Column(name = "delivery_personnel_id", nullable = false)
    private Long deliveryPersonnelId;

    @Column(name = "shipping_provider_id")
    private Long shippingProviderId;

    @Column(name = "collection_date")
    private LocalDateTime collectionDate;

    @Column(name = "verification_code", length = 10)
    private String verificationCode;

    @Column(name = "verification_method", length = 20)
    private String verificationMethod;

    @Column(name = "customer_signature", columnDefinition = "TEXT")
    private String customerSignature;

    @Column(name = "collection_notes", columnDefinition = "TEXT")
    private String collectionNotes;

    @Column(name = "failure_reason", columnDefinition = "TEXT")
    private String failureReason;

    @Column(name = "reconciliation_date")
    private LocalDateTime reconciliationDate;

    @Column(name = "reconciliation_reference", length = 50)
    private String reconciliationReference;

    /**
     * Cash on delivery status enumeration
     */
    public enum CodStatus {
        PENDING_COLLECTION,   // Payment awaiting collection by delivery person
        COLLECTED,            // Payment successfully collected
        COLLECTION_FAILED,    // Delivery person could not collect payment
        PENDING_DEPOSIT,      // Payment collected but not yet deposited to platform
        DEPOSITED,            // Payment deposited to platform account
        RECONCILED            // Payment reconciled and confirmed
    }
} 
