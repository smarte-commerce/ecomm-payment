package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Map;

/**
 * Request model for generating tax reports
 */
public class TaxReportRequest {
    @NotNull
    private String reportType;
    @NotNull
    private Instant startDate;
    @NotNull
    private Instant endDate;
    private String jurisdiction;
    private String taxType;
    private String format; // PDF, CSV, XML
    private Map<String, Object> filters;

    // Getters and setters
    public String getReportType() {
      return reportType;
    }

    public void setReportType(String reportType) {
      this.reportType = reportType;
    }

    public Instant getStartDate() {
      return startDate;
    }

    public void setStartDate(Instant startDate) {
      this.startDate = startDate;
    }

    public Instant getEndDate() {
      return endDate;
    }

    public void setEndDate(Instant endDate) {
      this.endDate = endDate;
    }

    public String getJurisdiction() {
      return jurisdiction;
    }

    public void setJurisdiction(String jurisdiction) {
      this.jurisdiction = jurisdiction;
    }

    public String getTaxType() {
      return taxType;
    }

    public void setTaxType(String taxType) {
      this.taxType = taxType;
    }

    public String getFormat() {
      return format;
    }

    public void setFormat(String format) {
      this.format = format;
    }

    public Map<String, Object> getFilters() {
      return filters;
    }

    public void setFilters(Map<String, Object> filters) {
      this.filters = filters;
    }
} 
