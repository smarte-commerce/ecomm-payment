package com.winnguyen1905.payment.core.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.SubscriptionRequest;
import com.winnguyen1905.payment.core.model.response.SubscriptionResponse;
import com.winnguyen1905.payment.persistance.entity.ESubscription.SubscriptionStatus;

/**
 * Service interface for managing subscriptions.
 */
public interface SubscriptionService {
    
    /**
     * Create a new subscription
     * 
     * @param request Subscription details
     * @return Created subscription
     */
    SubscriptionResponse createSubscription(SubscriptionRequest request);
    
    /**
     * Get a subscription by ID
     * 
     * @param id Subscription ID
     * @return Subscription details
     */
    SubscriptionResponse getSubscriptionById(UUID id);
    
    /**
     * Get subscription by subscription number
     * 
     * @param subscriptionNumber Subscription number
     * @return Subscription details
     */
    SubscriptionResponse getSubscriptionByNumber(String subscriptionNumber);
    
    /**
     * Get subscription by provider subscription ID
     * 
     * @param providerSubscriptionId Provider subscription ID
     * @return Subscription details
     */
    SubscriptionResponse getSubscriptionByProviderSubscriptionId(String providerSubscriptionId);
    
    /**
     * Get all subscriptions for a customer
     * 
     * @param customerId Customer ID
     * @return List of subscriptions
     */
    List<SubscriptionResponse> getSubscriptionsByCustomerId(Long customerId);
    
    /**
     * Get subscriptions by status
     * 
     * @param status Subscription status
     * @return List of subscriptions
     */
    List<SubscriptionResponse> getSubscriptionsByStatus(SubscriptionStatus status);
    
    /**
     * Get subscriptions by customer and status
     * 
     * @param customerId Customer ID
     * @param status Subscription status
     * @return List of subscriptions
     */
    List<SubscriptionResponse> getSubscriptionsByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);
    
    /**
     * Get subscriptions by plan ID
     * 
     * @param planId Plan ID
     * @return List of subscriptions
     */
    List<SubscriptionResponse> getSubscriptionsByPlanId(String planId);
    
    /**
     * Get active subscriptions ready for renewal
     * 
     * @param beforeDate Date before which subscriptions should be renewed
     * @return List of subscriptions ready for renewal
     */
    List<SubscriptionResponse> getActiveSubscriptionsToRenew(Instant beforeDate);
    
    /**
     * Get trialing subscriptions ready for conversion
     * 
     * @param beforeDate Date before which trial periods end
     * @return List of subscriptions ready for conversion
     */
    List<SubscriptionResponse> getTrialingSubscriptionsToConvert(Instant beforeDate);
    
    /**
     * Update subscription
     * 
     * @param id Subscription ID
     * @param request Updated subscription details
     * @return Updated subscription
     */
    SubscriptionResponse updateSubscription(UUID id, SubscriptionRequest request);
    
    /**
     * Update subscription status
     * 
     * @param id Subscription ID
     * @param status New status
     * @return Updated subscription
     */
    SubscriptionResponse updateSubscriptionStatus(UUID id, SubscriptionStatus status);
    
    /**
     * Cancel subscription
     * 
     * @param id Subscription ID
     * @return Updated subscription
     */
    SubscriptionResponse cancelSubscription(UUID id);
    
    /**
     * Reactivate cancelled subscription
     * 
     * @param id Subscription ID
     * @return Updated subscription
     */
    SubscriptionResponse reactivateSubscription(UUID id);
    
    /**
     * Renew subscription for next billing cycle
     * 
     * @param id Subscription ID
     * @return Updated subscription with new billing period
     */
    SubscriptionResponse renewSubscription(UUID id);
    
    /**
     * Convert trial subscription to active
     * 
     * @param id Subscription ID
     * @return Updated subscription
     */
    SubscriptionResponse convertTrialToActive(UUID id);
    
    /**
     * Update subscription payment method
     * 
     * @param id Subscription ID
     * @param paymentMethodId New payment method ID
     * @return Updated subscription
     */
    SubscriptionResponse updatePaymentMethod(UUID id, UUID paymentMethodId);
    
    /**
     * Update subscription metadata
     * 
     * @param id Subscription ID
     * @param metadata Metadata as JSON string
     * @return Updated subscription
     */
    SubscriptionResponse updateSubscriptionMetadata(UUID id, String metadata);
    
    /**
     * Pause subscription (suspend billing)
     * 
     * @param id Subscription ID
     * @return Updated subscription
     */
    SubscriptionResponse pauseSubscription(UUID id);
    
    /**
     * Resume paused subscription
     * 
     * @param id Subscription ID
     * @return Updated subscription
     */
    SubscriptionResponse resumeSubscription(UUID id);
    
    /**
     * Delete subscription
     * 
     * @param id Subscription ID
     */
    void deleteSubscription(UUID id);
} 
