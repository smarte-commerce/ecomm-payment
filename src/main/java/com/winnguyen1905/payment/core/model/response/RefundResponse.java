package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.ERefund.RefundReason;
import com.winnguyen1905.payment.persistance.entity.ERefund.RefundStatus;
import com.winnguyen1905.payment.persistance.entity.ERefund.RefundType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for refund details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundResponse {
    
    private UUID id;
    
    private UUID paymentId;
    
    private Long orderId;
    
    private String refundNumber;
    
    private BigDecimal amount;
    
    private String currency;
    
    private RefundType refundType;
    
    private RefundReason reason;
    
    private String description;
    
    private RefundStatus status;
    
    private String providerRefundId;
    
    private String gatewayResponse;
    
    private String failureReason;
    
    private Instant processedAt;
    
    private Instant completedAt;
    
    private Instant createdAt;
    
    private Instant updatedAt;
} 
