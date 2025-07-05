package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentStatus;
import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for payment details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    
    private UUID id;
    
    private Long orderId;
    
    private Long customerId;
    
    private String paymentNumber;
    
    private UUID providerId;
    
    private String providerName;
    
    private UUID paymentMethodId;
    
    private String paymentMethodDisplayName;
    
    private String paymentIntentId;
    
    private BigDecimal amount;
    
    private String currency;
    
    private BigDecimal feeAmount;
    
    private BigDecimal netAmount;
    
    private PaymentStatus status;
    
    private PaymentType paymentType;
    
    private String description;
    
    private String metadata;
    
    private String authorizationCode;
    
    private String transactionId;
    
    private String gatewayResponse;
    
    private String failureReason;
    
    private Instant authorizedAt;
    
    private Instant capturedAt;
    
    private Instant completedAt;
    
    private Instant expiresAt;
    
    private Instant createdAt;
    
    private Instant updatedAt;
} 
