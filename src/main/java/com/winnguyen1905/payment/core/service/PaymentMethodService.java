package com.winnguyen1905.payment.core.service;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.PaymentMethodRequest;
import com.winnguyen1905.payment.core.model.response.PaymentMethodResponse;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.MethodType;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.VerificationStatus;

/**
 * Service interface for managing payment methods.
 */
public interface PaymentMethodService {
    
    /**
     * Create a new payment method
     * 
     * @param request Payment method details
     * @return Created payment method
     */
    PaymentMethodResponse createPaymentMethod(PaymentMethodRequest request);
    
    /**
     * Get a payment method by ID
     * 
     * @param id Payment method ID
     * @return Payment method details
     */
    PaymentMethodResponse getPaymentMethodById(UUID id);
    
    /**
     * Get all payment methods for a customer
     * 
     * @param customerId Customer ID
     * @return List of payment methods
     */
    List<PaymentMethodResponse> getPaymentMethodsByCustomerId(Long customerId);
    
    /**
     * Get active payment methods for a customer
     * 
     * @param customerId Customer ID
     * @return List of active payment methods
     */
    List<PaymentMethodResponse> getActivePaymentMethodsByCustomerId(Long customerId);
    
    /**
     * Get default payment method for a customer
     * 
     * @param customerId Customer ID
     * @return Default payment method
     */
    PaymentMethodResponse getDefaultPaymentMethodByCustomerId(Long customerId);
    
    /**
     * Get payment methods by method type
     * 
     * @param methodType Method type
     * @return List of payment methods
     */
    List<PaymentMethodResponse> getPaymentMethodsByMethodType(MethodType methodType);
    
    /**
     * Get payment method by provider payment method ID
     * 
     * @param providerPaymentMethodId Provider payment method ID
     * @return Payment method details
     */
    PaymentMethodResponse getPaymentMethodByProviderPaymentMethodId(String providerPaymentMethodId);
    
    /**
     * Get active payment methods by customer and type
     * 
     * @param customerId Customer ID
     * @param methodType Method type
     * @return List of active payment methods
     */
    List<PaymentMethodResponse> getActivePaymentMethodsByCustomerAndType(Long customerId, MethodType methodType);
    
    /**
     * Update payment method
     * 
     * @param id Payment method ID
     * @param request Updated payment method details
     * @return Updated payment method
     */
    PaymentMethodResponse updatePaymentMethod(UUID id, PaymentMethodRequest request);
    
    /**
     * Set payment method as default
     * 
     * @param id Payment method ID
     * @return Updated payment method
     */
    PaymentMethodResponse setAsDefault(UUID id);
    
    /**
     * Deactivate payment method
     * 
     * @param id Payment method ID
     * @return Updated payment method
     */
    PaymentMethodResponse deactivatePaymentMethod(UUID id);
    
    /**
     * Update verification status
     * 
     * @param id Payment method ID
     * @param status Verification status
     * @return Updated payment method
     */
    PaymentMethodResponse updateVerificationStatus(UUID id, VerificationStatus status);
    
    /**
     * Delete payment method
     * 
     * @param id Payment method ID
     */
    void deletePaymentMethod(UUID id);
} 
