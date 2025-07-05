package com.winnguyen1905.payment.core.model.response;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for payment webhook details.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookResponse {
    
    private UUID id;
    
    private UUID providerId;
    
    private String providerName;
    
    private String webhookType;
    
    private UUID paymentId;
    
    private String paymentNumber;
    
    private UUID subscriptionId;
    
    private String subscriptionNumber;
    
    private String eventId;
    
    private String eventType;
    
    private String webhookData;
    
    private Boolean processed;
    
    private Integer processingAttempts;
    
    private String lastProcessingError;
    
    private Instant receivedAt;
    
    private Instant processedAt;
    
    private Instant createdAt;
    
    private Instant updatedAt;
} 
