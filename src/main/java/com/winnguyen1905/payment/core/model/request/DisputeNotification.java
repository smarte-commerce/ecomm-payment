package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Dispute notification DTO
 */
public class DisputeNotification {
    private String disputeId;
    private Long customerId;
    private String customerEmail;
    private String customerName;
    private String paymentId;
    private BigDecimal disputeAmount;
    private String currency;
    private String disputeReason;
    private LocalDateTime disputeDate;
    private List<String> notificationChannels;
    
    // Getters and setters
    public String getDisputeId() { return disputeId; }
    public void setDisputeId(String disputeId) { this.disputeId = disputeId; }
    
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    
    public BigDecimal getDisputeAmount() { return disputeAmount; }
    public void setDisputeAmount(BigDecimal disputeAmount) { this.disputeAmount = disputeAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getDisputeReason() { return disputeReason; }
    public void setDisputeReason(String disputeReason) { this.disputeReason = disputeReason; }
    
    public LocalDateTime getDisputeDate() { return disputeDate; }
    public void setDisputeDate(LocalDateTime disputeDate) { this.disputeDate = disputeDate; }
    
    public List<String> getNotificationChannels() { return notificationChannels; }
    public void setNotificationChannels(List<String> notificationChannels) { this.notificationChannels = notificationChannels; }
} 
