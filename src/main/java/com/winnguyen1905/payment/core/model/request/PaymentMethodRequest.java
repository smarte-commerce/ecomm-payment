package com.winnguyen1905.payment.core.model.request;

import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.MethodType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating payment methods.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodRequest {
    
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    
    private UUID providerId;
    
    @NotNull(message = "Method type cannot be null")
    private MethodType methodType;
    
    @NotBlank(message = "Display name cannot be blank")
    private String displayName;
    
    private Boolean isDefault;
    
    private Boolean isActive;
    
    private String cardLastFour;
    
    private String cardBrand;
    
    private Integer cardExpiryMonth;
    
    private Integer cardExpiryYear;
    
    private String billingAddress;
    
    private String providerPaymentMethodId;
} 
