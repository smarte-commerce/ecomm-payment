package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response model for tax validation
 */
public class TaxValidationResponse {
    private Boolean valid;
    private String message;
    private BigDecimal expectedTaxAmount;
    private BigDecimal actualTaxAmount;
    private BigDecimal difference;
    private List<String> validationErrors;

    // Getters and setters
    public Boolean getValid() { 
        return valid; 
    }
    
    public void setValid(Boolean valid) { 
        this.valid = valid; 
    }
    
    public String getMessage() { 
        return message; 
    }
    
    public void setMessage(String message) { 
        this.message = message; 
    }
    
    public BigDecimal getExpectedTaxAmount() { 
        return expectedTaxAmount; 
    }
    
    public void setExpectedTaxAmount(BigDecimal expectedTaxAmount) { 
        this.expectedTaxAmount = expectedTaxAmount; 
    }
    
    public BigDecimal getActualTaxAmount() { 
        return actualTaxAmount; 
    }
    
    public void setActualTaxAmount(BigDecimal actualTaxAmount) { 
        this.actualTaxAmount = actualTaxAmount; 
    }
    
    public BigDecimal getDifference() { 
        return difference; 
    }
    
    public void setDifference(BigDecimal difference) { 
        this.difference = difference; 
    }
    
    public List<String> getValidationErrors() { 
        return validationErrors; 
    }
    
    public void setValidationErrors(List<String> validationErrors) { 
        this.validationErrors = validationErrors; 
    }
} 
