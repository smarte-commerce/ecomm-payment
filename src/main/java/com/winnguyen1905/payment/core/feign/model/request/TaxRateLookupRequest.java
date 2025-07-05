package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;

/**
 * Request model for looking up tax rates
 */
public class TaxRateLookupRequest {
    @NotNull
    private TaxAddress address;
    @NotNull
    private String productCategory;
    private String taxType;
    private Instant effectiveDate;

    // Getters and setters
    public TaxAddress getAddress() {
      return address;
    }

    public void setAddress(TaxAddress address) {
      this.address = address;
    }

    public String getProductCategory() {
      return productCategory;
    }

    public void setProductCategory(String productCategory) {
      this.productCategory = productCategory;
    }

    public String getTaxType() {
      return taxType;
    }

    public void setTaxType(String taxType) {
      this.taxType = taxType;
    }

    public Instant getEffectiveDate() {
      return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
      this.effectiveDate = effectiveDate;
    }
} 
