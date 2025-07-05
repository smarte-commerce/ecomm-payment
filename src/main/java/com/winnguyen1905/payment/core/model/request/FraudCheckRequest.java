package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckType;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.RiskLevel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating fraud checks.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckRequest {
    
    private UUID paymentId;
    
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    
    @NotNull(message = "Check type cannot be null")
    private CheckType checkType;
    
    @NotNull(message = "Risk score cannot be null")
    @Positive(message = "Risk score must be positive")
    private BigDecimal riskScore;
    
    @NotNull(message = "Risk level cannot be null")
    private RiskLevel riskLevel;
    
    private String rulesTriggered;
    
    private String providerResponse;
    
    private Long reviewerId;
    
    private String reviewerNotes;
    
    private String decisionReason;
} 
