package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for notifying vendor account service about a new settlement.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementNotificationRequest {
    
    @NotNull
    private UUID settlementId;
    
    @NotNull
    private Long orderId;
    
    @NotBlank
    private String transactionId;
    
    @NotNull
    @Positive
    private BigDecimal settlementAmount;
    
    @NotBlank
    private String currency;
    
    @NotNull
    private LocalDateTime settlementDate;
    
    private String paymentReference;
    
    private String settlementMethod;
    
    @NotNull
    @Positive
    private BigDecimal commissionAmount;
    
    private String commissionDetails;
    
    private String settlementStatus;
} 
