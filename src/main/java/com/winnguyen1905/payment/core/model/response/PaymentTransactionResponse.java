package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.PaymentTransaction.PaymentMethod;
import com.winnguyen1905.payment.persistance.entity.PaymentTransaction.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionResponse {
    
    private UUID id;
    
    private Long orderId;
    
    private Long customerId;
    
    private Long vendorId;
    
    private String transactionId;
    
    private BigDecimal amount;
    
    private String currency;
    
    private PaymentMethod paymentMethod;
    
    private PaymentStatus status;
    
    private String paymentGateway;
    
    private String paymentGatewayReference;
    
    private String paymentDetails;
    
    private String failureReason;
    
    private LocalDateTime paymentInitiatedAt;
    
    private LocalDateTime paymentCompletedAt;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
} 
