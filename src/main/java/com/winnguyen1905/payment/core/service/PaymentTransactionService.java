package com.winnguyen1905.payment.core.service;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.PaymentTransactionRequest;
import com.winnguyen1905.payment.core.model.response.PaymentTransactionResponse;
import com.winnguyen1905.payment.persistance.entity.PaymentTransaction.PaymentStatus;

public interface PaymentTransactionService {
    
    /**
     * Create a new payment transaction
     * 
     * @param request Payment transaction details
     * @return Created payment transaction
     */
    PaymentTransactionResponse createPaymentTransaction(PaymentTransactionRequest request);
    
    /**
     * Get a payment transaction by ID
     * 
     * @param id Payment transaction ID
     * @return Payment transaction details
     */
    PaymentTransactionResponse getPaymentTransactionById(UUID id);
    
    /**
     * Get payment transaction by transaction ID
     * 
     * @param transactionId External transaction ID
     * @return Payment transaction details
     */
    PaymentTransactionResponse getPaymentTransactionByTransactionId(String transactionId);
    
    /**
     * Get all payment transactions for an order
     * 
     * @param orderId Order ID
     * @return List of payment transactions
     */
    List<PaymentTransactionResponse> getPaymentTransactionsByOrderId(Long orderId);
    
    /**
     * Get all payment transactions for a customer
     * 
     * @param customerId Customer ID
     * @return List of payment transactions
     */
    List<PaymentTransactionResponse> getPaymentTransactionsByCustomerId(Long customerId);
    
    /**
     * Get all payment transactions for a vendor
     * 
     * @param vendorId Vendor ID
     * @return List of payment transactions
     */
    List<PaymentTransactionResponse> getPaymentTransactionsByVendorId(Long vendorId);
    
    /**
     * Update payment transaction status
     * 
     * @param id Payment transaction ID
     * @param status New status
     * @return Updated payment transaction
     */
    PaymentTransactionResponse updatePaymentStatus(UUID id, PaymentStatus status);
    
    /**
     * Process payment completion
     * 
     * @param id Payment transaction ID
     * @param gatewayReference Payment gateway reference
     * @return Updated payment transaction
     */
    PaymentTransactionResponse completePayment(UUID id, String gatewayReference);
    
    /**
     * Mark payment as failed with reason
     * 
     * @param id Payment transaction ID
     * @param failureReason Reason for failure
     * @return Updated payment transaction
     */
    PaymentTransactionResponse failPayment(UUID id, String failureReason);
    
    /**
     * Process refund for a payment transaction
     * 
     * @param id Payment transaction ID
     * @param amount Refund amount (null for full refund)
     * @return Updated payment transaction
     */
    PaymentTransactionResponse refundPayment(UUID id, java.math.BigDecimal amount);
} 
