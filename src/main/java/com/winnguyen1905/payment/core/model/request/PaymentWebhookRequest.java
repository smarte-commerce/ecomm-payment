package com.winnguyen1905.payment.core.model.request;

import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating payment webhooks.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentWebhookRequest {
    
    @NotNull(message = "Provider ID cannot be null")
    private UUID providerId;
    
    @NotBlank(message = "Webhook type cannot be blank")
    private String webhookType;
    
    private UUID paymentId;
    
    private UUID subscriptionId;
    
    private String eventId;
    
    @NotBlank(message = "Event type cannot be blank")
    private String eventType;
    
    @NotBlank(message = "Webhook data cannot be blank")
    private String webhookData;
    
    private Boolean processed;
    
    private Integer processingAttempts;
    
    private String lastProcessingError;
    
    private Instant receivedAt;
    
    private Instant processedAt;
} 
