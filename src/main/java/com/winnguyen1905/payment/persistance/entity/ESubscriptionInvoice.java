package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "subscription_invoices")
public class ESubscriptionInvoice extends EBaseAudit {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subscription_id")
  private ESubscription subscription;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private EPayment payment;

  @Column(name = "invoice_number", nullable = false, unique = true)
  private String invoiceNumber;

  @Column(name = "amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private InvoiceStatus status;

  @Column(name = "provider_invoice_id")
  private String providerInvoiceId;

  @Column(name = "period_start", nullable = false)
  private Instant periodStart;

  @Column(name = "period_end", nullable = false)
  private Instant periodEnd;

  @Column(name = "due_date", nullable = false)
  private Instant dueDate;

  @Column(name = "paid_at")
  private Instant paidAt;

  @Column(name = "next_payment_attempt")
  private Instant nextPaymentAttempt;

  @Column(name = "attempt_count")
  private Integer attemptCount;

  @Column(name = "metadata", columnDefinition = "json")
  private String metadata;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  public enum InvoiceStatus {
    DRAFT, OPEN, PAID, UNCOLLECTIBLE, VOID
  }
}
