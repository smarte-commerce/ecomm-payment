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
 * Manages held funds with release conditions and timeout logic.
 * Tracks funds in escrow until delivery confirmation or expiration.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "escrow_accounts", indexes = {
    @Index(name = "idx_escrow_order_id", columnList = "order_id"),
    @Index(name = "idx_escrow_vendor_id", columnList = "vendor_id"),
    @Index(name = "idx_escrow_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_escrow_status", columnList = "status"),
    @Index(name = "idx_escrow_release_due_date", columnList = "release_due_date")
})
public class EscrowAccount extends BaseAuditEntity {

    @NotNull
    @Column(name = "order_id", nullable = false)
    private Long orderId;

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

    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EscrowStatus status;

    @NotNull
    @Column(name = "hold_period_days", nullable = false)
    private Integer holdPeriodDays;

    @NotNull
    @Column(name = "release_due_date", nullable = false)
    private LocalDateTime releaseDueDate;

    @Column(name = "release_condition", columnDefinition = "TEXT")
    private String releaseCondition;

    @Column(name = "release_confirmed_by")
    private String releaseConfirmedBy;

    @Column(name = "release_confirmed_at")
    private LocalDateTime releaseConfirmedAt;

    @Column(name = "auto_release")
    private Boolean autoRelease;

    @Column(name = "released_amount", precision = 19, scale = 4)
    private BigDecimal releasedAmount;

    @Column(name = "release_note", columnDefinition = "TEXT")
    private String releaseNote;

    /**
     * Escrow status enumeration
     */
    public enum EscrowStatus {
        HELD,             // Funds are being held in escrow
        PARTIALLY_RELEASED, // Partial release has occurred
        RELEASED,         // Funds have been fully released
        REFUNDED,         // Funds have been refunded to customer
        DISPUTED,         // Funds are in dispute
        EXPIRED           // Escrow hold period has expired
    }
} 
