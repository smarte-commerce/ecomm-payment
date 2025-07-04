package com.winnguyen1905.payment.persistance.entity;

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
@Table(name = "payment_webhooks")
public class EPaymentWebhook extends EBaseAudit {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "provider_id")
  private EPaymentProvider provider;

  @Column(name = "webhook_type", nullable = false)
  private String webhookType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_id")
  private EPayment payment;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subscription_id")
  private ESubscription subscription;

  @Column(name = "event_id")
  private String eventId;

  @Column(name = "event_type", nullable = false)
  private String eventType;

  @Column(name = "webhook_data", columnDefinition = "json", nullable = false)
  private String webhookData;

  @Column(name = "processed")
  private Boolean processed;

  @Column(name = "processing_attempts")
  private Integer processingAttempts;

  @Column(name = "last_processing_error", columnDefinition = "TEXT")
  private String lastProcessingError;

  @Column(name = "received_at")
  private Instant receivedAt;

  @Column(name = "processed_at")
  private Instant processedAt;
}
