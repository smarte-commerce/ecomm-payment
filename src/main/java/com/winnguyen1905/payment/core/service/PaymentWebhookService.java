package com.winnguyen1905.payment.core.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.PaymentWebhookRequest;
import com.winnguyen1905.payment.core.model.response.PaymentWebhookResponse;

/**
 * Service interface for managing payment webhooks and webhook processing.
 */
public interface PaymentWebhookService {
    
    /**
     * Create a new payment webhook
     * 
     * @param request Payment webhook details
     * @return Created payment webhook
     */
    PaymentWebhookResponse createPaymentWebhook(PaymentWebhookRequest request);
    
    /**
     * Get a payment webhook by ID
     * 
     * @param id Payment webhook ID
     * @return Payment webhook details
     */
    PaymentWebhookResponse getPaymentWebhookById(UUID id);
    
    /**
     * Get all payment webhooks
     * 
     * @return List of payment webhooks
     */
    List<PaymentWebhookResponse> getAllPaymentWebhooks();
    
    /**
     * Get payment webhooks by provider ID
     * 
     * @param providerId Provider ID
     * @return List of payment webhooks for the provider
     */
    List<PaymentWebhookResponse> getPaymentWebhooksByProviderId(UUID providerId);
    
    /**
     * Get payment webhooks by payment ID
     * 
     * @param paymentId Payment ID
     * @return List of payment webhooks for the payment
     */
    List<PaymentWebhookResponse> getPaymentWebhooksByPaymentId(UUID paymentId);
    
    /**
     * Get payment webhooks by subscription ID
     * 
     * @param subscriptionId Subscription ID
     * @return List of payment webhooks for the subscription
     */
    List<PaymentWebhookResponse> getPaymentWebhooksBySubscriptionId(UUID subscriptionId);
    
    /**
     * Get payment webhooks by webhook type
     * 
     * @param webhookType Webhook type
     * @return List of payment webhooks of the specified type
     */
    List<PaymentWebhookResponse> getPaymentWebhooksByWebhookType(String webhookType);
    
    /**
     * Get payment webhooks by event type
     * 
     * @param eventType Event type
     * @return List of payment webhooks of the specified event type
     */
    List<PaymentWebhookResponse> getPaymentWebhooksByEventType(String eventType);
    
    /**
     * Get payment webhook by event ID
     * 
     * @param eventId Event ID
     * @return Payment webhook details
     */
    PaymentWebhookResponse getPaymentWebhookByEventId(String eventId);
    
    /**
     * Get processed payment webhooks
     * 
     * @return List of processed payment webhooks
     */
    List<PaymentWebhookResponse> getProcessedPaymentWebhooks();
    
    /**
     * Get unprocessed payment webhooks
     * 
     * @return List of unprocessed payment webhooks
     */
    List<PaymentWebhookResponse> getUnprocessedPaymentWebhooks();
    
    /**
     * Get payment webhooks with processing errors
     * 
     * @return List of payment webhooks with errors
     */
    List<PaymentWebhookResponse> getPaymentWebhooksWithErrors();
    
    /**
     * Get payment webhooks received within a date range
     * 
     * @param startDate Start date
     * @param endDate End date
     * @return List of payment webhooks received within the range
     */
    List<PaymentWebhookResponse> getPaymentWebhooksReceivedBetween(Instant startDate, Instant endDate);
    
    /**
     * Get payment webhooks with high processing attempts
     * 
     * @param minAttempts Minimum number of processing attempts
     * @return List of payment webhooks with high retry counts
     */
    List<PaymentWebhookResponse> getPaymentWebhooksWithHighRetryCount(Integer minAttempts);
    
    /**
     * Update payment webhook
     * 
     * @param id Payment webhook ID
     * @param request Updated payment webhook details
     * @return Updated payment webhook
     */
    PaymentWebhookResponse updatePaymentWebhook(UUID id, PaymentWebhookRequest request);
    
    /**
     * Process payment webhook
     * 
     * @param id Payment webhook ID
     * @return Updated payment webhook after processing
     */
    PaymentWebhookResponse processPaymentWebhook(UUID id);
    
    /**
     * Mark payment webhook as processed
     * 
     * @param id Payment webhook ID
     * @return Updated payment webhook
     */
    PaymentWebhookResponse markAsProcessed(UUID id);
    
    /**
     * Mark payment webhook as failed with error
     * 
     * @param id Payment webhook ID
     * @param errorMessage Error message
     * @return Updated payment webhook
     */
    PaymentWebhookResponse markAsFailed(UUID id, String errorMessage);
    
    /**
     * Retry processing failed webhook
     * 
     * @param id Payment webhook ID
     * @return Updated payment webhook
     */
    PaymentWebhookResponse retryProcessing(UUID id);
    
    /**
     * Update webhook data
     * 
     * @param id Payment webhook ID
     * @param webhookData New webhook data
     * @return Updated payment webhook
     */
    PaymentWebhookResponse updateWebhookData(UUID id, String webhookData);
    
    /**
     * Verify webhook signature (provider-specific)
     * 
     * @param providerId Provider ID
     * @param payload Webhook payload
     * @param signature Webhook signature
     * @return True if signature is valid
     */
    boolean verifyWebhookSignature(UUID providerId, String payload, String signature);
    
    /**
     * Process webhook payload and extract information
     * 
     * @param providerId Provider ID
     * @param payload Webhook payload
     * @return Processed webhook response
     */
    PaymentWebhookResponse processWebhookPayload(UUID providerId, String payload);
    
    /**
     * Auto-retry failed webhooks
     * 
     * @param maxAttempts Maximum retry attempts
     * @return Number of webhooks retried
     */
    int autoRetryFailedWebhooks(Integer maxAttempts);
    
    /**
     * Delete payment webhook
     * 
     * @param id Payment webhook ID
     */
    void deletePaymentWebhook(UUID id);
    
    /**
     * Clean up old processed webhooks
     * 
     * @param olderThan Delete webhooks older than this date
     * @return Number of webhooks deleted
     */
    int cleanupOldWebhooks(Instant olderThan);
} 
