package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refunds")
public class ERefund extends EBaseAudit {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private EPayment payment;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Column(name = "refund_number", nullable = false, unique = true)
  private String refundNumber;

  @Column(name = "amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(name = "refund_type", nullable = false)
  private RefundType refundType;

  @Enumerated(EnumType.STRING)
  @Column(name = "reason", nullable = false)
  private RefundReason reason;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private RefundStatus status;

  @Column(name = "provider_refund_id")
  private String providerRefundId;

  @Column(name = "gateway_response", columnDefinition = "json")
  private String gatewayResponse;

  @Column(name = "failure_reason", columnDefinition = "TEXT")
  private String failureReason;

  @Column(name = "processed_at")
  private Instant processedAt;

  @Column(name = "completed_at")
  private Instant completedAt;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @OneToMany(mappedBy = "refund")
  private List<ERefundItem> refundItems;

  public enum RefundType {
    FULL, PARTIAL, CHARGEBACK
  }

  public enum RefundReason {
    CUSTOMER_REQUEST, FRAUD, DUPLICATE, PRODUCT_NOT_RECEIVED, PRODUCT_DEFECTIVE, CHARGEBACK, OTHER
  }

  public enum RefundStatus {
    PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED
  }
}
