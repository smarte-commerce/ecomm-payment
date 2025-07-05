package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.ESubscription.IntervalType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating subscriptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequest {
    
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    
    @NotBlank(message = "Subscription number cannot be blank")
    private String subscriptionNumber;
    
    private UUID paymentMethodId;
    
    private UUID providerId;
    
    private String providerSubscriptionId;
    
    @NotBlank(message = "Plan name cannot be blank")
    private String planName;
    
    @NotBlank(message = "Plan ID cannot be blank")
    private String planId;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    @NotNull(message = "Interval type cannot be null")
    private IntervalType intervalType;
    
    @NotNull(message = "Interval count cannot be null")
    @Positive(message = "Interval count must be positive")
    private Integer intervalCount;
    
    private Integer trialPeriodDays;
    
    @NotNull(message = "Current period start cannot be null")
    private Instant currentPeriodStart;
    
    @NotNull(message = "Current period end cannot be null")
    private Instant currentPeriodEnd;
    
    private Instant trialStart;
    
    private Instant trialEnd;
    
    private String metadata;
} 
