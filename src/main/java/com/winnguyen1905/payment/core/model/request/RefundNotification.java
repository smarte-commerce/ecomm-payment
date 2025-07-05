package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Refund notification DTO
 */
public class RefundNotification {
    private Long customerId;
    private String customerEmail;
    private String customerName;
    private String refundId;
    private String orderNumber;
    private BigDecimal refundAmount;
    private String currency;
    private String refundReason;
    private LocalDateTime refundDate;
    private List<String> notificationChannels;
    
    // Getters and setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getRefundId() { return refundId; }
    public void setRefundId(String refundId) { this.refundId = refundId; }
    
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    
    public BigDecimal getRefundAmount() { return refundAmount; }
    public void setRefundAmount(BigDecimal refundAmount) { this.refundAmount = refundAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getRefundReason() { return refundReason; }
    public void setRefundReason(String refundReason) { this.refundReason = refundReason; }
    
    public LocalDateTime getRefundDate() { return refundDate; }
    public void setRefundDate(LocalDateTime refundDate) { this.refundDate = refundDate; }
    
    public List<String> getNotificationChannels() { return notificationChannels; }
    public void setNotificationChannels(List<String> notificationChannels) { this.notificationChannels = notificationChannels; }
} 
