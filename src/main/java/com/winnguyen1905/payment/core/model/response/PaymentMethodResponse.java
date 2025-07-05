package com.winnguyen1905.payment.core.model.response;

import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.MethodType;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.VerificationStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for payment method details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponse {
    
    private UUID id;
    
    private Long customerId;
    
    private UUID providerId;
    
    private String providerName;
    
    private MethodType methodType;
    
    private String displayName;
    
    private Boolean isDefault;
    
    private Boolean isActive;
    
    private String cardLastFour;
    
    private String cardBrand;
    
    private Integer cardExpiryMonth;
    
    private Integer cardExpiryYear;
    
    private String billingAddress;
    
    private String providerPaymentMethodId;
    
    private VerificationStatus verificationStatus;
    
    private Instant createdAt;
    
    private Instant updatedAt;
} 
