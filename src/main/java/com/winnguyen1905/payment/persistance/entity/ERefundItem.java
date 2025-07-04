package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "refund_items")
public class ERefundItem extends EBaseAudit {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "refund_id")
  private ERefund refund;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_item_id")
  private EPaymentItem paymentItem;

  @Column(name = "order_item_id")
  private Long orderItemId;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "unit_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal unitAmount;

  @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal totalAmount;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Column(name = "reason")
  private String reason;

  @Column(name = "created_at")
  private Instant createdAt;
}
