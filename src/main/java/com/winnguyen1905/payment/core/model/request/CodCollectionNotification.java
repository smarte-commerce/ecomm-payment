package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * COD collection notification DTO
 */
public class CodCollectionNotification {
    private Long customerId;
    private String customerEmail;
    private String customerName;
    private String orderNumber;
    private BigDecimal codAmount;
    private String currency;
    private String collectionStatus;
    private LocalDateTime collectionDate;
    private String deliveryPersonnel;
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
    
    public BigDecimal getCodAmount() { return codAmount; }
    public void setCodAmount(BigDecimal codAmount) { this.codAmount = codAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getCollectionStatus() { return collectionStatus; }
    public void setCollectionStatus(String collectionStatus) { this.collectionStatus = collectionStatus; }
    
    public LocalDateTime getCollectionDate() { return collectionDate; }
    public void setCollectionDate(LocalDateTime collectionDate) { this.collectionDate = collectionDate; }
    
    public String getDeliveryPersonnel() { return deliveryPersonnel; }
    public void setDeliveryPersonnel(String deliveryPersonnel) { this.deliveryPersonnel = deliveryPersonnel; }
    
    public List<String> getNotificationChannels() { return notificationChannels; }
    public void setNotificationChannels(List<String> notificationChannels) { this.notificationChannels = notificationChannels; }
} 
