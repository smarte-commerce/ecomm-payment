package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;

/**
 * Address model for tax calculation and validation
 */
public class TaxAddress {
    @NotNull
    private String country;
    private String state;
    private String city;
    private String postalCode;
    private String street;

    // Getters and setters
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
    
    public String getStreet() { 
        return street; 
    }
    
    public void setStreet(String street) { 
        this.street = street; 
    }
} 
