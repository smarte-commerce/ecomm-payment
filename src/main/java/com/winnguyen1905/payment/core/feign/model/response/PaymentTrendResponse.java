package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Response DTO for payment trend analytics
 */
public class PaymentTrendResponse {
    private Instant timestamp;
    private BigDecimal amount;
    private Long transactionCount;
    private BigDecimal successRate;
    private String period; // HOUR, DAY, WEEK, MONTH

    // Getters and setters
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public Long getTransactionCount() { return transactionCount; }
    public void setTransactionCount(Long transactionCount) { this.transactionCount = transactionCount; }
    
    public BigDecimal getSuccessRate() { return successRate; }
    public void setSuccessRate(BigDecimal successRate) { this.successRate = successRate; }
    
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
} 
