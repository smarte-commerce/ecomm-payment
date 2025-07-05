package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;

/**
 * Request model for validating tax numbers
 */
public class TaxNumberValidationRequest {
    @NotNull
    private String taxNumber;
    @NotNull
    private String country;
    private String taxType; // VAT, EIN, GST, etc.
    private String businessName;

    // Getters and setters
    public String getTaxNumber() {
      return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
      this.taxNumber = taxNumber;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getTaxType() {
      return taxType;
    }

    public void setTaxType(String taxType) {
      this.taxType = taxType;
    }

    public String getBusinessName() {
      return businessName;
    }

    public void setBusinessName(String businessName) {
      this.businessName = businessName;
    }
} 
