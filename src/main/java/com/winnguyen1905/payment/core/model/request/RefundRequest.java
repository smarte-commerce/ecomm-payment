package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.ERefund.RefundReason;
import com.winnguyen1905.payment.persistance.entity.ERefund.RefundType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating refunds.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequest {
    
    @NotNull(message = "Payment ID cannot be null")
    private UUID paymentId;
    
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    
    @NotBlank(message = "Refund number cannot be blank")
    private String refundNumber;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    @NotNull(message = "Refund type cannot be null")
    private RefundType refundType;
    
    @NotNull(message = "Reason cannot be null")
    private RefundReason reason;
    
    private String description;
    
    private String providerRefundId;
    
    private String gatewayResponse;
} 
