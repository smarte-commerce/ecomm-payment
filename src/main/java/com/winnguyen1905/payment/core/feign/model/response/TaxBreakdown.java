package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;

/**
 * Model for tax breakdown details
 */
public class TaxBreakdown {
    private String taxType;
    private String jurisdiction;
    private BigDecimal rate;
    private BigDecimal taxableAmount;
    private BigDecimal taxAmount;

    // Getters and setters
    public String getTaxType() { 
        return taxType; 
    }
    
    public void setTaxType(String taxType) { 
        this.taxType = taxType; 
    }
    
    public String getJurisdiction() { 
        return jurisdiction; 
    }
    
    public void setJurisdiction(String jurisdiction) { 
        this.jurisdiction = jurisdiction; 
    }
    
    public BigDecimal getRate() { 
        return rate; 
    }
    
    public void setRate(BigDecimal rate) { 
        this.rate = rate; 
    }
    
    public BigDecimal getTaxableAmount() { 
        return taxableAmount; 
    }
    
    public void setTaxableAmount(BigDecimal taxableAmount) { 
        this.taxableAmount = taxableAmount; 
    }
    
    public BigDecimal getTaxAmount() { 
        return taxAmount; 
    }
    
    public void setTaxAmount(BigDecimal taxAmount) { 
        this.taxAmount = taxAmount; 
    }
} 
