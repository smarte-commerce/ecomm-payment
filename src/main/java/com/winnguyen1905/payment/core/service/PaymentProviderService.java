package com.winnguyen1905.payment.core.service;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.PaymentProviderRequest;
import com.winnguyen1905.payment.core.model.response.PaymentProviderResponse;
import com.winnguyen1905.payment.persistance.entity.EPaymentProvider.ProviderType;

/**
 * Service interface for managing payment providers.
 */
public interface PaymentProviderService {
    
    /**
     * Create a new payment provider
     * 
     * @param request Payment provider details
     * @return Created payment provider
     */
    PaymentProviderResponse createPaymentProvider(PaymentProviderRequest request);
    
    /**
     * Get a payment provider by ID
     * 
     * @param id Payment provider ID
     * @return Payment provider details
     */
    PaymentProviderResponse getPaymentProviderById(UUID id);
    
    /**
     * Get all payment providers
     * 
     * @return List of payment providers
     */
    List<PaymentProviderResponse> getAllPaymentProviders();
    
    /**
     * Get payment provider by provider code
     * 
     * @param providerCode Provider code
     * @return Payment provider details
     */
    PaymentProviderResponse getPaymentProviderByCode(String providerCode);
    
    /**
     * Get active payment providers
     * 
     * @return List of active payment providers
     */
    List<PaymentProviderResponse> getActivePaymentProviders();
    
    /**
     * Get payment providers by type
     * 
     * @param providerType Provider type
     * @return List of payment providers
     */
    List<PaymentProviderResponse> getPaymentProvidersByType(ProviderType providerType);
    
    /**
     * Get active payment providers by type
     * 
     * @param providerType Provider type
     * @return List of active payment providers
     */
    List<PaymentProviderResponse> getActivePaymentProvidersByType(ProviderType providerType);
    
    /**
     * Update payment provider
     * 
     * @param id Payment provider ID
     * @param request Updated payment provider details
     * @return Updated payment provider
     */
    PaymentProviderResponse updatePaymentProvider(UUID id, PaymentProviderRequest request);
    
    /**
     * Activate payment provider
     * 
     * @param id Payment provider ID
     * @return Updated payment provider
     */
    PaymentProviderResponse activatePaymentProvider(UUID id);
    
    /**
     * Deactivate payment provider
     * 
     * @param id Payment provider ID
     * @return Updated payment provider
     */
    PaymentProviderResponse deactivatePaymentProvider(UUID id);
    
    /**
     * Update provider configuration
     * 
     * @param id Payment provider ID
     * @param configuration Configuration as JSON string
     * @return Updated payment provider
     */
    PaymentProviderResponse updateConfiguration(UUID id, String configuration);
    
    /**
     * Update fee structure
     * 
     * @param id Payment provider ID
     * @param feeStructure Fee structure as JSON string
     * @return Updated payment provider
     */
    PaymentProviderResponse updateFeeStructure(UUID id, String feeStructure);
    
    /**
     * Delete payment provider
     * 
     * @param id Payment provider ID
     */
    void deletePaymentProvider(UUID id);
} 
