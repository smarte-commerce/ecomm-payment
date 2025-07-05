package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Request DTO for generating analytics reports
 */
public class ReportGenerationRequest {
    @NotNull
    private String reportType; // PAYMENT_SUMMARY, REVENUE_ANALYSIS, FRAUD_REPORT, PERFORMANCE_REPORT
    
    @NotNull
    private Instant startDate;
    
    @NotNull
    private Instant endDate;
    
    private UUID vendorId;
    private String format; // PDF, CSV, EXCEL, JSON
    private List<String> metrics;
    private Map<String, Object> filters;
    private String deliveryMethod; // DOWNLOAD, EMAIL, S3
    private String deliveryTarget; // Email address or S3 bucket

    // Getters and setters
    public String getReportType() { return reportType; }
    public void setReportType(String reportType) { this.reportType = reportType; }
    
    public Instant getStartDate() { return startDate; }
    public void setStartDate(Instant startDate) { this.startDate = startDate; }
    
    public Instant getEndDate() { return endDate; }
    public void setEndDate(Instant endDate) { this.endDate = endDate; }
    
    public UUID getVendorId() { return vendorId; }
    public void setVendorId(UUID vendorId) { this.vendorId = vendorId; }
    
    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
    
    public List<String> getMetrics() { return metrics; }
    public void setMetrics(List<String> metrics) { this.metrics = metrics; }
    
    public Map<String, Object> getFilters() { return filters; }
    public void setFilters(Map<String, Object> filters) { this.filters = filters; }
    
    public String getDeliveryMethod() { return deliveryMethod; }
    public void setDeliveryMethod(String deliveryMethod) { this.deliveryMethod = deliveryMethod; }
    
    public String getDeliveryTarget() { return deliveryTarget; }
    public void setDeliveryTarget(String deliveryTarget) { this.deliveryTarget = deliveryTarget; }
} 
