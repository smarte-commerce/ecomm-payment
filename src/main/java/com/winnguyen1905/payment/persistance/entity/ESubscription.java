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
@Table(name = "subscriptions")
public class ESubscription extends EBaseAudit {

  @Column(name = "customer_id", nullable = false)
  private Long customerId;

  @Column(name = "subscription_number", nullable = false, unique = true)
  private String subscriptionNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_method_id")
  private EPaymentMethod paymentMethod;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "provider_id")
  private EPaymentProvider provider;

  @Column(name = "provider_subscription_id")
  private String providerSubscriptionId;

  @Column(name = "plan_name", nullable = false)
  private String planName;

  @Column(name = "plan_id", nullable = false)
  private String planId;

  @Column(name = "amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Enumerated(EnumType.STRING)
  @Column(name = "interval_type", nullable = false)
  private IntervalType intervalType;

  @Column(name = "interval_count", nullable = false)
  private Integer intervalCount;

  @Column(name = "trial_period_days")
  private Integer trialPeriodDays;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private SubscriptionStatus status;

  @Column(name = "current_period_start", nullable = false)
  private Instant currentPeriodStart;

  @Column(name = "current_period_end", nullable = false)
  private Instant currentPeriodEnd;

  @Column(name = "trial_start")
  private Instant trialStart;

  @Column(name = "trial_end")
  private Instant trialEnd;

  @Column(name = "cancelled_at")
  private Instant cancelledAt;

  @Column(name = "ended_at")
  private Instant endedAt;

  @Column(name = "metadata", columnDefinition = "json")
  private String metadata;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @OneToMany(mappedBy = "subscription")
  private List<ESubscriptionInvoice> invoices;

  @OneToMany(mappedBy = "subscription")
  private List<EPaymentWebhook> webhooks;

  public enum IntervalType {
    DAY, WEEK, MONTH, YEAR
  }

  public enum SubscriptionStatus {
    ACTIVE, TRIALING, PAST_DUE, CANCELLED, UNPAID, INCOMPLETE
  }
}
