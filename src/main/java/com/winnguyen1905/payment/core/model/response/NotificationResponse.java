package com.winnguyen1905.payment.core.model.response;

import java.time.LocalDateTime;

/**
 * Base notification response DTO
 */
public class NotificationResponse {
    private String notificationId;
    private String status;
    private String message;
    private LocalDateTime sentAt;
    
    // Getters and setters
    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
} 
