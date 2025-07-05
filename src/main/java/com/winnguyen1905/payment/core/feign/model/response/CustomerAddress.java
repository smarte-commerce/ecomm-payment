package com.winnguyen1905.payment.core.feign.model.response;

/**
 * Response DTO for customer address information
 */
public class CustomerAddress {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String addressType;
    
    // Getters and setters
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getAddressType() { return addressType; }
    public void setAddressType(String addressType) { this.addressType = addressType; }
} 
