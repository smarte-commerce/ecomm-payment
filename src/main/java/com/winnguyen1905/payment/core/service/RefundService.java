package com.winnguyen1905.payment.core.service;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.RefundRequest;
import com.winnguyen1905.payment.core.model.response.RefundResponse;
import com.winnguyen1905.payment.persistance.entity.ERefund.RefundStatus;

/**
 * Service interface for managing refunds.
 */
public interface RefundService {
    
    /**
     * Create a new refund
     * 
     * @param request Refund details
     * @return Created refund
     */
    RefundResponse createRefund(RefundRequest request);
    
    /**
     * Get a refund by ID
     * 
     * @param id Refund ID
     * @return Refund details
     */
    RefundResponse getRefundById(UUID id);
    
    /**
     * Get refund by refund number
     * 
     * @param refundNumber Refund number
     * @return Refund details
     */
    RefundResponse getRefundByRefundNumber(String refundNumber);
    
    /**
     * Get refund by provider refund ID
     * 
     * @param providerRefundId Provider refund ID
     * @return Refund details
     */
    RefundResponse getRefundByProviderRefundId(String providerRefundId);
    
    /**
     * Get all refunds for a payment
     * 
     * @param paymentId Payment ID
     * @return List of refunds
     */
    List<RefundResponse> getRefundsByPaymentId(UUID paymentId);
    
    /**
     * Get all refunds for an order
     * 
     * @param orderId Order ID
     * @return List of refunds
     */
    List<RefundResponse> getRefundsByOrderId(Long orderId);
    
    /**
     * Get refunds by status
     * 
     * @param status Refund status
     * @return List of refunds
     */
    List<RefundResponse> getRefundsByStatus(RefundStatus status);
    
    /**
     * Update refund status
     * 
     * @param id Refund ID
     * @param status New status
     * @return Updated refund
     */
    RefundResponse updateRefundStatus(UUID id, RefundStatus status);
    
    /**
     * Process refund
     * 
     * @param id Refund ID
     * @return Updated refund
     */
    RefundResponse processRefund(UUID id);
    
    /**
     * Complete refund
     * 
     * @param id Refund ID
     * @return Updated refund
     */
    RefundResponse completeRefund(UUID id);
    
    /**
     * Fail refund with reason
     * 
     * @param id Refund ID
     * @param failureReason Reason for failure
     * @return Updated refund
     */
    RefundResponse failRefund(UUID id, String failureReason);
    
    /**
     * Cancel refund
     * 
     * @param id Refund ID
     * @return Updated refund
     */
    RefundResponse cancelRefund(UUID id);
    
    /**
     * Update gateway response
     * 
     * @param id Refund ID
     * @param gatewayResponse Gateway response as JSON string
     * @return Updated refund
     */
    RefundResponse updateGatewayResponse(UUID id, String gatewayResponse);
} 
