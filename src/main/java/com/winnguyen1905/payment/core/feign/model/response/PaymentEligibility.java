package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for customer payment eligibility information
 */
public class PaymentEligibility {
    private Boolean eligible;
    private String reason;
    private List<String> allowedPaymentMethods;
    private BigDecimal maxTransactionAmount;
    private String currency;
    
    // Getters and setters
    public Boolean getEligible() { return eligible; }
    public void setEligible(Boolean eligible) { this.eligible = eligible; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public List<String> getAllowedPaymentMethods() { return allowedPaymentMethods; }
    public void setAllowedPaymentMethods(List<String> allowedPaymentMethods) { this.allowedPaymentMethods = allowedPaymentMethods; }
    
    public BigDecimal getMaxTransactionAmount() { return maxTransactionAmount; }
    public void setMaxTransactionAmount(BigDecimal maxTransactionAmount) { this.maxTransactionAmount = maxTransactionAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
} 
