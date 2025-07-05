package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Request model for tax validation
 */
public class TaxValidationRequest {
    @NotNull
    private BigDecimal originalAmount;
    
    @NotNull
    private BigDecimal taxAmount;
    
    @NotNull
    private TaxAddress address;
    
    @NotNull
    private String productCategory;

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
    
    public TaxAddress getAddress() { 
        return address; 
    }
    
    public void setAddress(TaxAddress address) { 
        this.address = address; 
    }
    
    public String getProductCategory() { 
        return productCategory; 
    }
    
    public void setProductCategory(String productCategory) { 
        this.productCategory = productCategory; 
    }
} 
