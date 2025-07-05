package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Response model for tax calculation
 */
public class TaxCalculationResponse {
    private BigDecimal originalAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String currency;
    private List<TaxBreakdown> breakdown;
    private String calculationId;
    private Instant calculatedAt;
    private Boolean exemptionApplied;
    private List<String> appliedExemptions;

    // Getters and setters
    public BigDecimal getOriginalAmount() { 
        return originalAmount; 
    }
    
    public void setOriginalAmount(BigDecimal originalAmount) { 
        this.originalAmount = originalAmount; 
    }
    
    public BigDecimal getTaxAmount() { 
        return taxAmount; 
    }
    
    public void setTaxAmount(BigDecimal taxAmount) { 
        this.taxAmount = taxAmount; 
    }
    
    public BigDecimal getTotalAmount() { 
        return totalAmount; 
    }
    
    public void setTotalAmount(BigDecimal totalAmount) { 
        this.totalAmount = totalAmount; 
    }
    
    public String getCurrency() { 
        return currency; 
    }
    
    public void setCurrency(String currency) { 
        this.currency = currency; 
    }
    
    public List<TaxBreakdown> getBreakdown() { 
        return breakdown; 
    }
    
    public void setBreakdown(List<TaxBreakdown> breakdown) { 
        this.breakdown = breakdown; 
    }
    
    public String getCalculationId() { 
        return calculationId; 
    }
    
    public void setCalculationId(String calculationId) { 
        this.calculationId = calculationId; 
    }
    
    public Instant getCalculatedAt() { 
        return calculatedAt; 
    }
    
    public void setCalculatedAt(Instant calculatedAt) { 
        this.calculatedAt = calculatedAt; 
    }
    
    public Boolean getExemptionApplied() { 
        return exemptionApplied; 
    }
    
    public void setExemptionApplied(Boolean exemptionApplied) { 
        this.exemptionApplied = exemptionApplied; 
    }
    
    public List<String> getAppliedExemptions() { 
        return appliedExemptions; 
    }
    
    public void setAppliedExemptions(List<String> appliedExemptions) { 
        this.appliedExemptions = appliedExemptions; 
    }
} 
