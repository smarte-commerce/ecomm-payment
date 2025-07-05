package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

/**
 * Response DTO for payment summary analytics
 */
public class PaymentSummaryResponse {
    private BigDecimal totalAmount;
    private String currency;
    private Long totalTransactions;
    private Long successfulTransactions;
    private Long failedTransactions;
    private BigDecimal successRate;
    private BigDecimal averageTransactionAmount;
    private Map<String, Long> transactionsByMethod;
    private Map<String, BigDecimal> amountsByMethod;
    private Instant calculatedAt;

    // Getters and setters
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public Long getTotalTransactions() { return totalTransactions; }
    public void setTotalTransactions(Long totalTransactions) { this.totalTransactions = totalTransactions; }
    
    public Long getSuccessfulTransactions() { return successfulTransactions; }
    public void setSuccessfulTransactions(Long successfulTransactions) { this.successfulTransactions = successfulTransactions; }
    
    public Long getFailedTransactions() { return failedTransactions; }
    public void setFailedTransactions(Long failedTransactions) { this.failedTransactions = failedTransactions; }
    
    public BigDecimal getSuccessRate() { return successRate; }
    public void setSuccessRate(BigDecimal successRate) { this.successRate = successRate; }
    
    public BigDecimal getAverageTransactionAmount() { return averageTransactionAmount; }
    public void setAverageTransactionAmount(BigDecimal averageTransactionAmount) { this.averageTransactionAmount = averageTransactionAmount; }
    
    public Map<String, Long> getTransactionsByMethod() { return transactionsByMethod; }
    public void setTransactionsByMethod(Map<String, Long> transactionsByMethod) { this.transactionsByMethod = transactionsByMethod; }
    
    public Map<String, BigDecimal> getAmountsByMethod() { return amountsByMethod; }
    public void setAmountsByMethod(Map<String, BigDecimal> amountsByMethod) { this.amountsByMethod = amountsByMethod; }
    
    public Instant getCalculatedAt() { return calculatedAt; }
    public void setCalculatedAt(Instant calculatedAt) { this.calculatedAt = calculatedAt; }
} 
