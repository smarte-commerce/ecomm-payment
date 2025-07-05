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
 * Tracks commission calculations and vendor payouts.
 * Manages the settlement process from escrow release to final vendor payment.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vendor_settlements", indexes = {
    @Index(name = "idx_settlement_vendor_id", columnList = "vendor_id"),
    @Index(name = "idx_settlement_order_id", columnList = "order_id"),
    @Index(name = "idx_settlement_transaction_id", columnList = "transaction_id"),
    @Index(name = "idx_settlement_status", columnList = "status"),
    @Index(name = "idx_settlement_settlement_date", columnList = "settlement_date")
})
public class VendorSettlement extends BaseAuditEntity {

  @NotNull
  @Column(name = "vendor_id", nullable = false)
  private Long vendorId;

  @NotNull
  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Size(max = 50)
  @Column(name = "transaction_id", nullable = false)
  private String transactionId;

  @Column(name = "settlement_batch_id")
  private String settlementBatchId;

  @NotNull
  @Positive
  @Column(name = "order_amount", nullable = false, precision = 19, scale = 4)
  private BigDecimal orderAmount;

  @NotNull
  @Positive
  @Column(name = "commission_amount", nullable = false, precision = 19, scale = 4)
  private BigDecimal commissionAmount;

  @Column(name = "additional_fees", precision = 19, scale = 4)
  private BigDecimal additionalFees;

  @NotNull
  @Positive
  @Column(name = "settlement_amount", nullable = false, precision = 19, scale = 4)
  private BigDecimal settlementAmount;

  @NotNull
  @Size(min = 3, max = 3)
  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private SettlementStatus status;

  @Column(name = "commission_rule_id")
  private Long commissionRuleId;

  @Column(name = "commission_calculation_details", columnDefinition = "json")
  private String commissionCalculationDetails;

  @Column(name = "settlement_due_date")
  private LocalDateTime settlementDueDate;

  @Column(name = "settlement_date")
  private LocalDateTime settlementDate;

  @Column(name = "payment_reference")
  private String paymentReference;

  @Column(name = "payment_method")
  private String paymentMethod;

  @Column(name = "bank_account_id")
  private String bankAccountId;

  @Column(name = "settlement_notes", columnDefinition = "TEXT")
  private String settlementNotes;

  @Column(name = "failure_reason", columnDefinition = "TEXT")
  private String failureReason;

  /**
   * Settlement status enumeration
   */
  public enum SettlementStatus {
    PENDING, // Settlement is pending
    CALCULATED, // Commission and settlement amount calculated
    SCHEDULED, // Settlement is scheduled for payment
    PROCESSING, // Settlement payment is processing
    COMPLETED, // Settlement payment completed
    FAILED, // Settlement payment failed
    CANCELLED // Settlement was cancelled
  }
}
