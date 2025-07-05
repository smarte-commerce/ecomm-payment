package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckStatus;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckType;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.RiskLevel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for fraud check details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FraudCheckResponse {
    
    private UUID id;
    
    private UUID paymentId;
    
    private Long orderId;
    
    private Long customerId;
    
    private CheckType checkType;
    
    private BigDecimal riskScore;
    
    private RiskLevel riskLevel;
    
    private CheckStatus status;
    
    private String rulesTriggered;
    
    private String providerResponse;
    
    private Long reviewerId;
    
    private String reviewerNotes;
    
    private String decisionReason;
    
    private Instant checkedAt;
    
    private Instant reviewedAt;
} 
