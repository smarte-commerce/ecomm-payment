package com.winnguyen1905.payment.core.feign.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Request DTO for updating customer payment history
 */
public class PaymentHistoryUpdateRequest {
    private String paymentId;
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String paymentMethod;
    private LocalDateTime transactionDate;
    
    // Getters and setters
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
} 
