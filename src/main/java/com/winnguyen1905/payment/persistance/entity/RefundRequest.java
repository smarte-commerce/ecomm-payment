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
 * Handles refund requests within the 3-day window with approval workflow.
 * Manages the refund request lifecycle from submission to processing.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refund_requests", indexes = {
    @Index(name = "idx_refund_order_id", columnList = "order_id"),
    @Index(name = "idx_refund_customer_id", columnList = "customer_id"),
    @Index(name = "idx_refund_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_refund_status", columnList = "status"),
    @Index(name = "idx_refund_request_date", columnList = "request_date")
})
public class RefundRequest extends BaseAuditEntity {

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
    @Column(name = "transaction_id", nullable = false)
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
    @Column(name = "reason_code", nullable = false)
    private RefundReason reasonCode;

    @NotNull
    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "refund_deadline", nullable = false)
    private LocalDateTime refundDeadline;

    @NotNull
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RefundStatus status;

    @Column(name = "customer_evidence", columnDefinition = "json")
    private String customerEvidence;

    @Column(name = "vendor_response", columnDefinition = "TEXT")
    private String vendorResponse;

    @Column(name = "reviewer_id")
    private Long reviewerId;

    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;

    @Column(name = "decision_notes", columnDefinition = "TEXT")
    private String decisionNotes;

    @Column(name = "refund_processed_at")
    private LocalDateTime refundProcessedAt;

    @Column(name = "refund_transaction_id")
    private String refundTransactionId;

    /**
     * Refund reason enumeration
     */
    public enum RefundReason {
        ITEM_NOT_RECEIVED,
        ITEM_NOT_AS_DESCRIBED,
        DAMAGED_ITEM,
        WRONG_ITEM,
        MISSING_PARTS,
        ARRIVED_TOO_LATE,
        DUPLICATE_ORDER,
        CHANGED_MIND,
        OTHER
    }

    /**
     * Refund status enumeration
     */
    public enum RefundStatus {
        REQUESTED,         // Initial request submitted
        UNDER_REVIEW,      // Being reviewed by platform
        VENDOR_RESPONSE_PENDING, // Waiting for vendor response
        APPROVED,          // Refund approved but not processed
        REJECTED,          // Refund request rejected
        PROCESSING,        // Refund is being processed
        PROCESSED,         // Refund has been processed
        CANCELLED          // Request was cancelled
    }
} 
