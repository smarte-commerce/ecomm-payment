package com.winnguyen1905.payment.core.feign.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request DTO for customer payment notifications
 */
public class CustomerPaymentNotificationRequest {
    private String eventType;
    private String paymentId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String message;
    private LocalDateTime eventTime;
    
    // Getters and setters
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getEventTime() { return eventTime; }
    public void setEventTime(LocalDateTime eventTime) { this.eventTime = eventTime; }
} 
