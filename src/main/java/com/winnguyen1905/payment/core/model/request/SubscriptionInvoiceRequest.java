package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.ESubscriptionInvoice.InvoiceStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating subscription invoices.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionInvoiceRequest {
    
    @NotNull(message = "Subscription ID cannot be null")
    private UUID subscriptionId;
    
    private UUID paymentId;
    
    @NotBlank(message = "Invoice number cannot be blank")
    private String invoiceNumber;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    private InvoiceStatus status;
    
    private String providerInvoiceId;
    
    @NotNull(message = "Period start cannot be null")
    private Instant periodStart;
    
    @NotNull(message = "Period end cannot be null")
    private Instant periodEnd;
    
    @NotNull(message = "Due date cannot be null")
    private Instant dueDate;
    
    private Instant paidAt;
    
    private Instant nextPaymentAttempt;
    
    private Integer attemptCount;
    
    private String metadata;
} 
