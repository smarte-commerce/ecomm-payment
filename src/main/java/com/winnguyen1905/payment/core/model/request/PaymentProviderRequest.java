package com.winnguyen1905.payment.core.model.request;

import com.winnguyen1905.payment.persistance.entity.EPaymentProvider.ProviderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating payment providers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProviderRequest {
    
    @NotBlank(message = "Provider name cannot be blank")
    private String providerName;
    
    @NotBlank(message = "Provider code cannot be blank")
    private String providerCode;
    
    @NotNull(message = "Provider type cannot be null")
    private ProviderType providerType;
    
    private Boolean isActive;
    
    private String supportedCountries;
    
    private String supportedCurrencies;
    
    private String apiEndpoint;
    
    private String webhookEndpoint;
    
    private String configuration;
    
    private String feeStructure;
} 
