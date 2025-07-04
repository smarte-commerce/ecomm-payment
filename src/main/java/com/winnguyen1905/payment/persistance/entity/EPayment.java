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
@Table(name = "payments")
public class EPayment extends EBaseAudit {
  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Column(name = "customer_id", nullable = false)
  private Long customerId;

  @Column(name = "payment_number", nullable = false, unique = true)
  private String paymentNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "provider_id")
  private EPaymentProvider provider;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_method_id")
  private EPaymentMethod paymentMethod;

  @Column(name = "payment_intent_id")
  private String paymentIntentId;

  @Column(name = "amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Column(name = "fee_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal feeAmount;

  @Column(name = "net_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal netAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private PaymentStatus status;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_type")
  private PaymentType paymentType;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "metadata", columnDefinition = "json")
  private String metadata;

  @Column(name = "authorization_code")
  private String authorizationCode;

  @Column(name = "transaction_id")
  private String transactionId;

  @Column(name = "gateway_response", columnDefinition = "json")
  private String gatewayResponse;

  @Column(name = "failure_reason", columnDefinition = "TEXT")
  private String failureReason;

  @Column(name = "authorized_at")
  private Instant authorizedAt;

  @Column(name = "captured_at")
  private Instant capturedAt;

  @Column(name = "completed_at")
  private Instant completedAt;

  @Column(name = "expires_at")
  private Instant expiresAt;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @OneToMany(mappedBy = "payment")
  private List<EPaymentItem> paymentItems;

  @OneToMany(mappedBy = "payment")
  private List<ERefund> refunds;

  @OneToMany(mappedBy = "payment")
  private List<EFraudCheck> fraudChecks;

  @OneToMany(mappedBy = "payment")
  private List<EPaymentWebhook> webhooks;

  @OneToMany(mappedBy = "payment")
  private List<EPaymentDispute> disputes;

  public enum PaymentStatus {
    PENDING, PROCESSING, AUTHORIZED, CAPTURED, COMPLETED, FAILED, CANCELLED, REFUNDED, PARTIALLY_REFUNDED
  }

  public enum PaymentType {
    PURCHASE, REFUND, SUBSCRIPTION, DEPOSIT, WITHDRAWAL
  }
}
