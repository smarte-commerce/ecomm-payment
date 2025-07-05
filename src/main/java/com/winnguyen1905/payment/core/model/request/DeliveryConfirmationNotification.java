package com.winnguyen1905.payment.core.model.request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Delivery confirmation notification DTO
 */
public class DeliveryConfirmationNotification {
    private Long customerId;
    private String customerEmail;
    private String customerName;
    private String orderNumber;
    private String trackingNumber;
    private LocalDateTime deliveryDate;
    private String confirmationStatus;
    private LocalDateTime autoConfirmDate;
    private List<String> notificationChannels;
    
    // Getters and setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
    
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDateTime deliveryDate) { this.deliveryDate = deliveryDate; }
    
    public String getConfirmationStatus() { return confirmationStatus; }
    public void setConfirmationStatus(String confirmationStatus) { this.confirmationStatus = confirmationStatus; }
    
    public LocalDateTime getAutoConfirmDate() { return autoConfirmDate; }
    public void setAutoConfirmDate(LocalDateTime autoConfirmDate) { this.autoConfirmDate = autoConfirmDate; }
    
    public List<String> getNotificationChannels() { return notificationChannels; }
    public void setNotificationChannels(List<String> notificationChannels) { this.notificationChannels = notificationChannels; }
} 
