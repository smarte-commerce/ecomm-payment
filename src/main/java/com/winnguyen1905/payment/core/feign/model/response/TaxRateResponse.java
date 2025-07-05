package com.winnguyen1905.payment.core.feign.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Response model for tax rate operations
 */
public class TaxRateResponse {
    private UUID rateId;
    private String name;
    private BigDecimal rate;
    private String taxType;
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private Instant effectiveDate;
    private Instant expiryDate;
    private Boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    // Getters and setters
    public UUID getRateId() { 
        return rateId; 
    }
    
    public void setRateId(UUID rateId) { 
        this.rateId = rateId; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public void setName(String name) { 
        this.name = name; 
    }
    
    public BigDecimal getRate() { 
        return rate; 
    }
    
    public void setRate(BigDecimal rate) { 
        this.rate = rate; 
    }
    
    public String getTaxType() { 
        return taxType; 
    }
    
    public void setTaxType(String taxType) { 
        this.taxType = taxType; 
    }
    
    public String getCountry() { 
        return country; 
    }
    
    public void setCountry(String country) { 
        this.country = country; 
    }
    
    public String getState() { 
        return state; 
    }
    
    public void setState(String state) { 
        this.state = state; 
    }
    
    public String getCity() { 
        return city; 
    }
    
    public void setCity(String city) { 
        this.city = city; 
    }
    
    public String getPostalCode() { 
        return postalCode; 
    }
    
    public void setPostalCode(String postalCode) { 
        this.postalCode = postalCode; 
    }
    
    public Instant getEffectiveDate() { 
        return effectiveDate; 
    }
    
    public void setEffectiveDate(Instant effectiveDate) { 
        this.effectiveDate = effectiveDate; 
    }
    
    public Instant getExpiryDate() { 
        return expiryDate; 
    }
    
    public void setExpiryDate(Instant expiryDate) { 
        this.expiryDate = expiryDate; 
    }
    
    public Boolean getActive() { 
        return active; 
    }
    
    public void setActive(Boolean active) { 
        this.active = active; 
    }
    
    public Instant getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(Instant createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public Instant getUpdatedAt() { 
        return updatedAt; 
    }
    
    public void setUpdatedAt(Instant updatedAt) { 
        this.updatedAt = updatedAt; 
    }
} 
