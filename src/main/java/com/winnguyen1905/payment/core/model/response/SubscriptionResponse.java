package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.ESubscription.IntervalType;
import com.winnguyen1905.payment.persistance.entity.ESubscription.SubscriptionStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for subscription details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponse {
    
    private UUID id;
    
    private Long customerId;
    
    private String subscriptionNumber;
    
    private UUID paymentMethodId;
    
    private String paymentMethodDisplayName;
    
    private UUID providerId;
    
    private String providerName;
    
    private String providerSubscriptionId;
    
    private String planName;
    
    private String planId;
    
    private BigDecimal amount;
    
    private String currency;
    
    private IntervalType intervalType;
    
    private Integer intervalCount;
    
    private Integer trialPeriodDays;
    
    private SubscriptionStatus status;
    
    private Instant currentPeriodStart;
    
    private Instant currentPeriodEnd;
    
    private Instant trialStart;
    
    private Instant trialEnd;
    
    private Instant cancelledAt;
    
    private Instant endedAt;
    
    private String metadata;
    
    private Instant createdAt;
    
    private Instant updatedAt;
} 
