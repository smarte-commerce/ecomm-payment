package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.ESubscriptionInvoice.InvoiceStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for subscription invoice details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionInvoiceResponse {
    
    private UUID id;
    
    private UUID subscriptionId;
    
    private String subscriptionNumber;
    
    private UUID paymentId;
    
    private String paymentNumber;
    
    private String invoiceNumber;
    
    private BigDecimal amount;
    
    private String currency;
    
    private InvoiceStatus status;
    
    private String providerInvoiceId;
    
    private Instant periodStart;
    
    private Instant periodEnd;
    
    private Instant dueDate;
    
    private Instant paidAt;
    
    private Instant nextPaymentAttempt;
    
    private Integer attemptCount;
    
    private String metadata;
    
    private Instant createdAt;
    
    private Instant updatedAt;
} 
