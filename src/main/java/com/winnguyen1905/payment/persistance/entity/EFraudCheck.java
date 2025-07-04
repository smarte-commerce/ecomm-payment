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
@Table(name = "fraud_checks")
public class EFraudCheck extends EBaseAudit {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private EPayment payment;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Column(name = "customer_id", nullable = false)
  private Long customerId;

  @Enumerated(EnumType.STRING)
  @Column(name = "check_type", nullable = false)
  private CheckType checkType;

  @Column(name = "risk_score", nullable = false, precision = 5, scale = 2)
  private BigDecimal riskScore;

  @Enumerated(EnumType.STRING)
  @Column(name = "risk_level", nullable = false)
  private RiskLevel riskLevel;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private CheckStatus status;

  @Column(name = "rules_triggered", columnDefinition = "json")
  private String rulesTriggered;

  @Column(name = "provider_response", columnDefinition = "json")
  private String providerResponse;

  @Column(name = "reviewer_id")
  private Long reviewerId;

  @Column(name = "reviewer_notes", columnDefinition = "TEXT")
  private String reviewerNotes;

  @Column(name = "decision_reason", columnDefinition = "TEXT")
  private String decisionReason;

  @Column(name = "checked_at")
  private Instant checkedAt;

  @Column(name = "reviewed_at")
  private Instant reviewedAt;

  public enum CheckType {
    AUTOMATED, MANUAL, PROVIDER
  }

  public enum RiskLevel {
    LOW, MEDIUM, HIGH, CRITICAL
  }

  public enum CheckStatus {
    PENDING, APPROVED, DECLINED, REVIEW_REQUIRED
  }
}
