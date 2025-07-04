package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.winnguyen1905.payment.persistance.entity.PaymentTransaction.PaymentMethod;

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
public class PaymentTransactionRequest {
    
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    
    @NotNull(message = "Vendor ID cannot be null")
    private Long vendorId;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    @NotNull(message = "Payment method cannot be null")
    private PaymentMethod paymentMethod;
    
    private String paymentGateway;
    
    private String paymentGatewayReference;
    
    private String paymentDetails;
    
    private LocalDateTime paymentInitiatedAt;
} 
