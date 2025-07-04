package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating a vendor settlement.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorSettlementRequest {
    
    @NotNull(message = "Vendor ID cannot be null")
    private Long vendorId;
    
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    
    @NotBlank(message = "Transaction ID cannot be blank")
    @Size(max = 50, message = "Transaction ID cannot exceed 50 characters")
    private String transactionId;
    
    private String settlementBatchId;
    
    @NotNull(message = "Order amount cannot be null")
    @Positive(message = "Order amount must be positive")
    private BigDecimal orderAmount;
    
    private BigDecimal commissionAmount;
    
    private BigDecimal additionalFees;
    
    private BigDecimal settlementAmount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    private Long commissionRuleId;
    
    private String commissionCalculationDetails;
    
    private LocalDateTime settlementDueDate;
    
    private String paymentMethod;
    
    private String bankAccountId;
    
    private String settlementNotes;
} 
