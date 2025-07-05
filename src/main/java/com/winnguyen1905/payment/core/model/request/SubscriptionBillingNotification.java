package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Subscription billing notification DTO
 */
public class SubscriptionBillingNotification {
    private Long customerId;
    private String customerEmail;
    private String customerName;
    private String subscriptionId;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime billingDate;
    private LocalDateTime nextBillingDate;
    private String billingStatus;
    private List<String> notificationChannels;
    
    // Getters and setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getSubscriptionId() { return subscriptionId; }
    public void setSubscriptionId(String subscriptionId) { this.subscriptionId = subscriptionId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public LocalDateTime getBillingDate() { return billingDate; }
    public void setBillingDate(LocalDateTime billingDate) { this.billingDate = billingDate; }
    
    public LocalDateTime getNextBillingDate() { return nextBillingDate; }
    public void setNextBillingDate(LocalDateTime nextBillingDate) { this.nextBillingDate = nextBillingDate; }
    
    public String getBillingStatus() { return billingStatus; }
    public void setBillingStatus(String billingStatus) { this.billingStatus = billingStatus; }
    
    public List<String> getNotificationChannels() { return notificationChannels; }
    public void setNotificationChannels(List<String> notificationChannels) { this.notificationChannels = notificationChannels; }
} 
