package com.winnguyen1905.payment.core.model.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Request model for tax filing operations
 */
public class TaxFilingRequest {
    @NotNull
    private String filingType;
    
    @NotNull
    private String jurisdiction;
    
    @NotNull
    private Instant periodStart;
    
    @NotNull
    private Instant periodEnd;
    
    private UUID reportId;
    private Map<String, BigDecimal> taxAmounts;
    private Map<String, Object> filingData;

    // Getters and setters
    public String getFilingType() {
        return filingType;
    }

    public void setFilingType(String filingType) {
        this.filingType = filingType;
    }

    public String getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Instant getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Instant periodStart) {
        this.periodStart = periodStart;
    }

    public Instant getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Instant periodEnd) {
        this.periodEnd = periodEnd;
    }

    public UUID getReportId() {
        return reportId;
    }

    public void setReportId(UUID reportId) {
        this.reportId = reportId;
    }

    public Map<String, BigDecimal> getTaxAmounts() {
        return taxAmounts;
    }

    public void setTaxAmounts(Map<String, BigDecimal> taxAmounts) {
        this.taxAmounts = taxAmounts;
    }

    public Map<String, Object> getFilingData() {
        return filingData;
    }

    public void setFilingData(Map<String, Object> filingData) {
        this.filingData = filingData;
    }
} 
