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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscrowAccountRequest {
    
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    
    @NotNull(message = "Vendor ID cannot be null")
    private Long vendorId;
    
    @NotBlank(message = "Transaction ID cannot be blank")
    @Size(max = 50, message = "Transaction ID cannot exceed 50 characters")
    private String transactionId;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    @NotNull(message = "Hold period days cannot be null")
    private Integer holdPeriodDays;
    
    private LocalDateTime releaseDueDate;
    
    private String releaseCondition;
    
    private Boolean autoRelease;
} 
