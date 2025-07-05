package com.winnguyen1905.payment.core.model.response;

import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EPaymentProvider.ProviderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for payment provider details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProviderResponse {
    
    private UUID id;
    
    private String providerName;
    
    private String providerCode;
    
    private ProviderType providerType;
    
    private Boolean isActive;
    
    private String supportedCountries;
    
    private String supportedCurrencies;
    
    private String apiEndpoint;
    
    private String webhookEndpoint;
    
    private String configuration;
    
    private String feeStructure;
    
    private Instant createdAt;
    
    private Instant updatedAt;
} 
