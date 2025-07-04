package com.winnguyen1905.payment.core.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.VendorSettlementRequest;
import com.winnguyen1905.payment.core.model.response.VendorAccountResponse;
import com.winnguyen1905.payment.core.model.response.VendorSettlementInfoResponse;
import com.winnguyen1905.payment.core.model.response.VendorSettlementResponse;
import com.winnguyen1905.payment.persistance.entity.VendorSettlement.SettlementStatus;

/**
 * Service interface for managing vendor settlements and communication with the Account service.
 */
public interface VendorSettlementService {
    
    /**
     * Create a new vendor settlement
     * 
     * @param request Vendor settlement details
     * @return Created vendor settlement
     */
    VendorSettlementResponse createSettlement(VendorSettlementRequest request);
    
    /**
     * Get a vendor settlement by ID
     * 
     * @param id Vendor settlement ID
     * @return Vendor settlement details
     */
    VendorSettlementResponse getSettlementById(UUID id);
    
    /**
     * Get all settlements for a vendor
     * 
     * @param vendorId Vendor ID
     * @return List of vendor settlements
     */
    List<VendorSettlementResponse> getSettlementsByVendorId(Long vendorId);
    
    /**
     * Get all settlements for an order
     * 
     * @param orderId Order ID
     * @return List of vendor settlements
     */
    List<VendorSettlementResponse> getSettlementsByOrderId(Long orderId);
    
    /**
     * Update vendor settlement status
     * 
     * @param id Vendor settlement ID
     * @param status New status
     * @return Updated vendor settlement
     */
    VendorSettlementResponse updateSettlementStatus(UUID id, SettlementStatus status);
    
    /**
     * Calculate commission for a settlement
     * 
     * @param vendorId Vendor ID
     * @param orderId Order ID
     * @param orderAmount Order amount
     * @return Commission amount
     */
    BigDecimal calculateCommission(Long vendorId, Long orderId, BigDecimal orderAmount);
    
    /**
     * Process payment for a settlement
     * 
     * @param id Vendor settlement ID
     * @return Updated vendor settlement with payment details
     */
    VendorSettlementResponse processSettlementPayment(UUID id);
    
    /**
     * Get all settlements due for payment
     * 
     * @param dueDate Settlements due before this date
     * @return List of settlements due for payment
     */
    List<VendorSettlementResponse> getSettlementsDueForPayment(LocalDateTime dueDate);
    
    /**
     * Notify vendor account service about a settlement
     * 
     * @param settlementId Settlement ID
     * @return True if notification was successful
     */
    boolean notifyVendorAccountService(UUID settlementId);
    
    /**
     * Get vendor account information from account service
     * 
     * @param vendorId Vendor ID
     * @return Vendor account information
     */
    VendorAccountResponse getVendorAccountInfo(Long vendorId);
    
    /**
     * Get vendor settlement information from account service
     * 
     * @param vendorId Vendor ID
     * @return Vendor settlement information
     */
    VendorSettlementInfoResponse getVendorSettlementInfo(Long vendorId);
} 
