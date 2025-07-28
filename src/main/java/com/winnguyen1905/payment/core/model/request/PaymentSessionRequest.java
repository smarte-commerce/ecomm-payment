package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating payment sessions with external gateways.
 * This creates a hosted checkout session that returns a payment URL.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionRequest {
    
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;
    
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    @NotBlank(message = "Currency cannot be blank")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;
    
    @NotBlank(message = "Callback URL cannot be blank")
    private String callbackUrl;
    
    private String cancelUrl;
    
    private String description;
    
    private String paymentMethods; // Comma-separated list of allowed payment methods
    
    private String metadata;
    
    private Long expiresInMinutes; // Session expiration time
    
    // Provider-specific fields
    private String locale; // Language preference (vi, en for VNPay)
    
    private String orderType; // Order type classification
    
    private String customerIp; // Customer IP address
    
    private String returnUrl; // Alternative return URL
    
    private Boolean generateQr; // Whether to generate QR code
    
    private String mobileAppScheme; // Mobile app deep link scheme
} 
