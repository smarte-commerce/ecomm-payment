package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.winnguyen1905.payment.persistance.entity.CommissionRule.RuleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating commission rules.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommissionRuleRequest {
    
    private Long vendorId;
    
    private Long categoryId;
    
    @NotBlank(message = "Rule name cannot be blank")
    @Size(max = 100, message = "Rule name cannot exceed 100 characters")
    private String ruleName;
    
    @NotNull(message = "Rule type cannot be null")
    private RuleType ruleType;
    
    @NotNull(message = "Commission rate cannot be null")
    @PositiveOrZero(message = "Commission rate must be positive or zero")
    private BigDecimal commissionRate;
    
    private BigDecimal fixedFeeAmount;
    
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    private BigDecimal minCommissionAmount;
    
    private BigDecimal maxCommissionAmount;
    
    private String tierStructure;
    
    @NotNull(message = "Effective date cannot be null")
    private LocalDateTime effectiveDate;
    
    private LocalDateTime expirationDate;
    
    private Boolean isActive;
    
    private Integer priority;
    
    private String description;
    
    private String additionalFees;
} 
