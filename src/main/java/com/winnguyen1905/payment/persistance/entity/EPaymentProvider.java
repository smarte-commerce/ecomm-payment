package com.winnguyen1905.payment.persistance.entity;

import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "payment_providers")
public class EPaymentProvider extends EBaseAudit {

  @Column(name = "provider_name", nullable = false, unique = true)
  private String providerName;

  @Column(name = "provider_code", nullable = false, unique = true)
  private String providerCode;

  @Enumerated(EnumType.STRING)
  @Column(name = "provider_type", nullable = false)
  private ProviderType providerType;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "supported_countries", columnDefinition = "json")
  private String supportedCountries;

  @Column(name = "supported_currencies", columnDefinition = "json")
  private String supportedCurrencies;

  @Column(name = "api_endpoint")
  private String apiEndpoint;

  @Column(name = "webhook_endpoint")
  private String webhookEndpoint;

  @Column(name = "configuration", columnDefinition = "json")
  private String configuration;

  @Column(name = "fee_structure", columnDefinition = "json")
  private String feeStructure;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  @OneToMany(mappedBy = "provider")
  private List<EPaymentMethod> paymentMethods;

  @OneToMany(mappedBy = "provider")
  private List<EPayment> payments;

  @OneToMany(mappedBy = "provider")
  private List<ESubscription> subscriptions;

  @OneToMany(mappedBy = "provider")
  private List<EPaymentWebhook> webhooks;

  public enum ProviderType {
    CREDIT_CARD, DIGITAL_WALLET, BANK_TRANSFER, CRYPTOCURRENCY, BNPL
  }
}
