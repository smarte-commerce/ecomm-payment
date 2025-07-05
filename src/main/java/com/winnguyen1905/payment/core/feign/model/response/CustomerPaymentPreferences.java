package com.winnguyen1905.payment.core.feign.model.response;

/**
 * Response DTO for customer payment preferences
 */
public class CustomerPaymentPreferences {
    private String preferredPaymentMethod;
    private String preferredCurrency;
    private Boolean autoPayEnabled;
    private Boolean savePaymentMethods;
    private Boolean receivePaymentNotifications;
    
    // Getters and setters
    public String getPreferredPaymentMethod() { return preferredPaymentMethod; }
    public void setPreferredPaymentMethod(String preferredPaymentMethod) { this.preferredPaymentMethod = preferredPaymentMethod; }
    
    public String getPreferredCurrency() { return preferredCurrency; }
    public void setPreferredCurrency(String preferredCurrency) { this.preferredCurrency = preferredCurrency; }
    
    public Boolean getAutoPayEnabled() { return autoPayEnabled; }
    public void setAutoPayEnabled(Boolean autoPayEnabled) { this.autoPayEnabled = autoPayEnabled; }
    
    public Boolean getSavePaymentMethods() { return savePaymentMethods; }
    public void setSavePaymentMethods(Boolean savePaymentMethods) { this.savePaymentMethods = savePaymentMethods; }
    
    public Boolean getReceivePaymentNotifications() { return receivePaymentNotifications; }
    public void setReceivePaymentNotifications(Boolean receivePaymentNotifications) { this.receivePaymentNotifications = receivePaymentNotifications; }
} 
