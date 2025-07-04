package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.VendorSettlement.SettlementStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for vendor settlement details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorSettlementResponse {
    
    private UUID id;
    
    private Long vendorId;
    
    private Long orderId;
    
    private String transactionId;
    
    private String settlementBatchId;
    
    private BigDecimal orderAmount;
    
    private BigDecimal commissionAmount;
    
    private BigDecimal additionalFees;
    
    private BigDecimal settlementAmount;
    
    private String currency;
    
    private SettlementStatus status;
    
    private Long commissionRuleId;
    
    private String commissionCalculationDetails;
    
    private LocalDateTime settlementDueDate;
    
    private LocalDateTime settlementDate;
    
    private String paymentReference;
    
    private String paymentMethod;
    
    private String bankAccountId;
    
    private String settlementNotes;
    
    private String failureReason;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 
