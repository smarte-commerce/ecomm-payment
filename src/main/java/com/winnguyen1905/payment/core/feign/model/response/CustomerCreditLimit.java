package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Response DTO for customer credit limit information
 */
public class CustomerCreditLimit {
    private BigDecimal creditLimit;
    private BigDecimal availableCredit;
    private BigDecimal usedCredit;
    private String currency;
    private LocalDateTime lastUpdated;
    
    // Getters and setters
    public BigDecimal getCreditLimit() { return creditLimit; }
    public void setCreditLimit(BigDecimal creditLimit) { this.creditLimit = creditLimit; }
    
    public BigDecimal getAvailableCredit() { return availableCredit; }
    public void setAvailableCredit(BigDecimal availableCredit) { this.availableCredit = availableCredit; }
    
    public BigDecimal getUsedCredit() { return usedCredit; }
    public void setUsedCredit(BigDecimal usedCredit) { this.usedCredit = usedCredit; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
} 
