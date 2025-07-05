package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

/**
 * Response DTO for revenue summary analytics
 */
public class RevenueSummaryResponse {
    private BigDecimal totalRevenue;
    private String currency;
    private BigDecimal netRevenue;
    private BigDecimal grossRevenue;
    private BigDecimal totalFees;
    private BigDecimal totalRefunds;
    private BigDecimal growthRate;
    private BigDecimal previousPeriodRevenue;
    private Map<String, BigDecimal> revenueBySource;
    private Instant calculatedAt;

    // Getters and setters
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public BigDecimal getNetRevenue() { return netRevenue; }
    public void setNetRevenue(BigDecimal netRevenue) { this.netRevenue = netRevenue; }
    
    public BigDecimal getGrossRevenue() { return grossRevenue; }
    public void setGrossRevenue(BigDecimal grossRevenue) { this.grossRevenue = grossRevenue; }
    
    public BigDecimal getTotalFees() { return totalFees; }
    public void setTotalFees(BigDecimal totalFees) { this.totalFees = totalFees; }
    
    public BigDecimal getTotalRefunds() { return totalRefunds; }
    public void setTotalRefunds(BigDecimal totalRefunds) { this.totalRefunds = totalRefunds; }
    
    public BigDecimal getGrowthRate() { return growthRate; }
    public void setGrowthRate(BigDecimal growthRate) { this.growthRate = growthRate; }
    
    public BigDecimal getPreviousPeriodRevenue() { return previousPeriodRevenue; }
    public void setPreviousPeriodRevenue(BigDecimal previousPeriodRevenue) { this.previousPeriodRevenue = previousPeriodRevenue; }
    
    public Map<String, BigDecimal> getRevenueBySource() { return revenueBySource; }
    public void setRevenueBySource(Map<String, BigDecimal> revenueBySource) { this.revenueBySource = revenueBySource; }
    
    public Instant getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(Instant calculatedAt) { this.calculatedAt = calculatedAt; }
} 
