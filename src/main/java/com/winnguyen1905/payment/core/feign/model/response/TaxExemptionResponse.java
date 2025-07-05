package com.winnguyen1905.payment.core.feign.model.response;

import java.time.Instant;
import java.util.UUID;

/**
 * Response model for tax exemption operations
 */
public class TaxExemptionResponse {
    private UUID exemptionId;
    private UUID customerId;
    private String exemptionType;
    private String exemptionCode;
    private String exemptionNumber;
    private String jurisdiction;
    private Instant effectiveDate;
    private Instant expiryDate;
    private String certificateUrl;
    private Boolean active;
    private Instant createdAt;

    // Getters and setters
    public UUID getExemptionId() {
      return exemptionId;
    }

    public void setExemptionId(UUID exemptionId) {
      this.exemptionId = exemptionId;
    }

    public UUID getCustomerId() {
      return customerId;
    }

    public void setCustomerId(UUID customerId) {
      this.customerId = customerId;
    }

    public String getExemptionType() {
      return exemptionType;
    }

    public void setExemptionType(String exemptionType) {
      this.exemptionType = exemptionType;
    }

    public String getExemptionCode() {
      return exemptionCode;
    }

    public void setExemptionCode(String exemptionCode) {
      this.exemptionCode = exemptionCode;
    }

    public String getExemptionNumber() {
      return exemptionNumber;
    }

    public void setExemptionNumber(String exemptionNumber) {
      this.exemptionNumber = exemptionNumber;
    }

    public String getJurisdiction() {
      return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
      this.jurisdiction = jurisdiction;
    }

    public Instant getEffectiveDate() {
      return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
      this.effectiveDate = effectiveDate;
    }

    public Instant getExpiryDate() {
      return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
      this.expiryDate = expiryDate;
    }

    public String getCertificateUrl() {
      return certificateUrl;
    }

    public void setCertificateUrl(String certificateUrl) {
      this.certificateUrl = certificateUrl;
    }

    public Boolean getActive() {
      return active;
    }

    public void setActive(Boolean active) {
      this.active = active;
    }

    public Instant getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
      this.createdAt = createdAt;
    }
} 
