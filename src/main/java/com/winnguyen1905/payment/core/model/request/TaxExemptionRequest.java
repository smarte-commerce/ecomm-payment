package com.winnguyen1905.payment.core.model.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

/**
 * Request model for tax exemption operations
 */
public class TaxExemptionRequest {
    @NotNull
    private UUID customerId;
    
    @NotNull
    private String exemptionType;
    
    @NotNull
    private String exemptionCode;
    
    private String exemptionNumber;
    private String jurisdiction;
    private Instant effectiveDate;
    private Instant expiryDate;
    private String certificateUrl;

    // Getters and setters
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
} 
