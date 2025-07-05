package com.winnguyen1905.payment.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.winnguyen1905.payment.core.feign.model.request.PaymentHistoryUpdateRequest;
import com.winnguyen1905.payment.core.feign.model.request.CustomerPaymentNotificationRequest;
import com.winnguyen1905.payment.core.feign.model.response.CustomerInfo;
import com.winnguyen1905.payment.core.feign.model.response.CustomerPaymentPreferences;
import com.winnguyen1905.payment.core.feign.model.response.CustomerAddress;
import com.winnguyen1905.payment.core.feign.model.response.CustomerCreditLimit;
import com.winnguyen1905.payment.core.feign.model.response.CustomerRiskProfile;
import com.winnguyen1905.payment.core.feign.model.response.PaymentEligibility;
import com.winnguyen1905.payment.core.model.response.RestResponse;

/**
 * Feign client for communicating with the Customer service.
 * Handles customer information retrieval and payment-related updates.
 */
@FeignClient(name = "customer-service", url = "${service.customer.url}")
public interface CustomerServiceClient {
    
    /**
     * Get customer details by customer ID
     * 
     * @param customerId Customer ID
     * @return Customer details
     */
    @GetMapping("/api/v1/customers/{customerId}")
    RestResponse<CustomerInfo> getCustomerById(@PathVariable("customerId") Long customerId);
    
    /**
     * Get customer payment preferences
     * 
     * @param customerId Customer ID
     * @return Customer payment preferences
     */
    @GetMapping("/api/v1/customers/{customerId}/payment-preferences")
    RestResponse<CustomerPaymentPreferences> getCustomerPaymentPreferences(@PathVariable("customerId") Long customerId);
    
    /**
     * Get customer billing address
     * 
     * @param customerId Customer ID
     * @return Customer billing address
     */
    @GetMapping("/api/v1/customers/{customerId}/billing-address")
    RestResponse<CustomerAddress> getCustomerBillingAddress(@PathVariable("customerId") Long customerId);
    
    /**
     * Get customer credit limit information
     * 
     * @param customerId Customer ID
     * @return Customer credit limit details
     */
    @GetMapping("/api/v1/customers/{customerId}/credit-limit")
    RestResponse<CustomerCreditLimit> getCustomerCreditLimit(@PathVariable("customerId") Long customerId);
    
    /**
     * Get customer risk profile for fraud checking
     * 
     * @param customerId Customer ID
     * @return Customer risk profile
     */
    @GetMapping("/api/v1/customers/{customerId}/risk-profile")
    RestResponse<CustomerRiskProfile> getCustomerRiskProfile(@PathVariable("customerId") Long customerId);
    
    /**
     * Update customer payment history
     * 
     * @param customerId Customer ID
     * @param paymentHistoryUpdate Payment history update
     * @return Result of the update
     */
    @PutMapping("/api/v1/customers/{customerId}/payment-history")
    RestResponse<Void> updateCustomerPaymentHistory(
            @PathVariable("customerId") Long customerId,
            @RequestBody PaymentHistoryUpdateRequest paymentHistoryUpdate);
    
    /**
     * Notify customer about payment events
     * 
     * @param customerId Customer ID
     * @param paymentNotification Payment notification details
     * @return Result of the notification
     */
    @PutMapping("/api/v1/customers/{customerId}/payment-notification")
    RestResponse<Void> notifyCustomerPaymentEvent(
            @PathVariable("customerId") Long customerId,
            @RequestBody CustomerPaymentNotificationRequest paymentNotification);
    
    /**
     * Check if customer is eligible for payment method
     * 
     * @param customerId Customer ID
     * @return Eligibility status
     */
    @GetMapping("/api/v1/customers/{customerId}/payment-eligibility")
    RestResponse<PaymentEligibility> checkPaymentEligibility(@PathVariable("customerId") Long customerId);
} 
