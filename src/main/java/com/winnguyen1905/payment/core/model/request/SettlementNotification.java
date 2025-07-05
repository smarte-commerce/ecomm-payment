package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Settlement notification DTO
 */
public class SettlementNotification {
    private Long vendorId;
    private String vendorEmail;
    private String vendorName;
    private String settlementId;
    private BigDecimal settlementAmount;
    private String currency;
    private LocalDateTime settlementDate;
    private List<String> notificationChannels;
    
    // Getters and setters
    public Long getVendorId() { return vendorId; }
    public void setVendorId(Long vendorId) { this.vendorId = vendorId; }
    
    public String getVendorEmail() { return vendorEmail; }
    public void setVendorEmail(String vendorEmail) { this.vendorEmail = vendorEmail; }
    
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    
    public String getSettlementId() { return settlementId; }
    public void setSettlementId(String settlementId) { this.settlementId = settlementId; }
    
    public BigDecimal getSettlementAmount() { return settlementAmount; }
    public void setSettlementAmount(BigDecimal settlementAmount) { this.settlementAmount = settlementAmount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public LocalDateTime getSettlementDate() { return settlementDate; }
    public void setSettlementDate(LocalDateTime settlementDate) { this.settlementDate = settlementDate; }
    
    public List<String> getNotificationChannels() { return notificationChannels; }
    public void setNotificationChannels(List<String> notificationChannels) { this.notificationChannels = notificationChannels; }
} 
