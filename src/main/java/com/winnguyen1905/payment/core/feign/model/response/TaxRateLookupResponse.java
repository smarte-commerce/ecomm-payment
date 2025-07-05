package com.winnguyen1905.payment.core.feign.model.response;

import java.time.Instant;
import java.util.List;

/**
 * Response model for tax rate lookup operations
 */
public class TaxRateLookupResponse {
    private List<TaxBreakdown> taxRates;
    private String jurisdiction;
    private Boolean ratesFound;
    private String source; // AUTHORITY, CACHE, THIRD_PARTY
    private Instant lookedUpAt;
    private String message;

    // Getters and setters
    public List<TaxBreakdown> getTaxRates() {
      return taxRates;
    }

    public void setTaxRates(List<TaxBreakdown> taxRates) {
      this.taxRates = taxRates;
    }

    public String getJurisdiction() {
      return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
      this.jurisdiction = jurisdiction;
    }

    public Boolean getRatesFound() {
      return ratesFound;
    }

    public void setRatesFound(Boolean ratesFound) {
      this.ratesFound = ratesFound;
    }

    public String getSource() {
      return source;
    }

    public void setSource(String source) {
      this.source = source;
    }

    public Instant getLookedUpAt() {
      return lookedUpAt;
    }

    public void setLookedUpAt(Instant lookedUpAt) {
      this.lookedUpAt = lookedUpAt;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
} 
