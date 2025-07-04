package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EscrowAccount.EscrowStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscrowAccountResponse {
    
    private UUID id;
    
    private Long orderId;
    
    private Long vendorId;
    
    private String transactionId;
    
    private BigDecimal amount;
    
    private String currency;
    
    private EscrowStatus status;
    
    private Integer holdPeriodDays;
    
    private LocalDateTime releaseDueDate;
    
    private String releaseCondition;
    
    private String releaseConfirmedBy;
    
    private LocalDateTime releaseConfirmedAt;
    
    private Boolean autoRelease;
    
    private BigDecimal releasedAmount;
    
    private String releaseNote;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 
