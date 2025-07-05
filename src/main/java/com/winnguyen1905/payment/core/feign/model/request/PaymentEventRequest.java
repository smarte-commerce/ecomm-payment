package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Request DTO for recording payment events in analytics service
 */
public class PaymentEventRequest {
  @NotNull
  private String eventType; // PAYMENT_CREATED, PAYMENT_COMPLETED, PAYMENT_FAILED, REFUND_ISSUED

  @NotNull
  private UUID paymentId;

  private UUID customerId;
  private UUID vendorId;

  @NotNull
  private BigDecimal amount;

  @NotNull
  private String currency;

  private String paymentMethod;
  private String gatewayProvider;
  private String status;
  private String failureReason;
  private Map<String, Object> metadata;

  @NotNull
  private Instant timestamp;

  // Getters and setters
  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public UUID getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(UUID paymentId) {
    this.paymentId = paymentId;
  }

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }

  public UUID getVendorId() {
    return vendorId;
  }

  public void setVendorId(UUID vendorId) {
    this.vendorId = vendorId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }

  public String getGatewayProvider() {
    return gatewayProvider;
  }

  public void setGatewayProvider(String gatewayProvider) {
    this.gatewayProvider = gatewayProvider;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFailureReason() {
    return failureReason;
  }

  public void setFailureReason(String failureReason) {
    this.failureReason = failureReason;
  }

  public Map<String, Object> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }
}
