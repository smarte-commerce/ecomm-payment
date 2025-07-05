package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.CommissionRule.RuleType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for commission rule details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommissionRuleResponse {
    
    private UUID id;
    
    private Long vendorId;
    
    private Long categoryId;
    
    private String ruleName;
    
    private RuleType ruleType;
    
    private BigDecimal commissionRate;
    
    private BigDecimal fixedFeeAmount;
    
    private String currency;
    
    private BigDecimal minCommissionAmount;
    
    private BigDecimal maxCommissionAmount;
    
    private String tierStructure;
    
    private LocalDateTime effectiveDate;
    
    private LocalDateTime expirationDate;
    
    private Boolean isActive;
    
    private Integer priority;
    
    private String description;
    
    private String additionalFees;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 
