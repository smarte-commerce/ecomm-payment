package com.winnguyen1905.payment.core.feign.model.response;

import java.time.Instant;
import java.util.List;

/**
 * Response model for tax number validation
 */
public class TaxNumberValidationResponse {
    private String taxNumber;
    private Boolean valid;
    private String status; // ACTIVE, INACTIVE, INVALID
    private String businessName;
    private String registeredAddress;
    private String validationSource;
    private Instant validatedAt;
    private List<String> validationErrors;

    // Getters and setters
    public String getTaxNumber() {
      return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
      this.taxNumber = taxNumber;
    }

    public Boolean getValid() {
      return valid;
    }

    public void setValid(Boolean valid) {
      this.valid = valid;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getBusinessName() {
      return businessName;
    }

    public void setBusinessName(String businessName) {
      this.businessName = businessName;
    }

    public String getRegisteredAddress() {
      return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
      this.registeredAddress = registeredAddress;
    }

    public String getValidationSource() {
      return validationSource;
    }

    public void setValidationSource(String validationSource) {
      this.validationSource = validationSource;
    }

    public Instant getValidatedAt() {
      return validatedAt;
    }

    public void setValidatedAt(Instant validatedAt) {
      this.validatedAt = validatedAt;
    }

    public List<String> getValidationErrors() {
      return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
      this.validationErrors = validationErrors;
    }
} 
