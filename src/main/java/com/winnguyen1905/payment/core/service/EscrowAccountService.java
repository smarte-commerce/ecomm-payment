package com.winnguyen1905.payment.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.EscrowAccountRequest;
import com.winnguyen1905.payment.core.model.response.EscrowAccountResponse;
import com.winnguyen1905.payment.persistance.entity.EscrowAccount.EscrowStatus;

public interface EscrowAccountService {
    
    /**
     * Create a new escrow account to hold funds
     * 
     * @param request Escrow account details
     * @return Created escrow account
     */
    EscrowAccountResponse createEscrowAccount(EscrowAccountRequest request);
    
    /**
     * Get an escrow account by ID
     * 
     * @param id Escrow account ID
     * @return Escrow account details
     */
    EscrowAccountResponse getEscrowAccountById(UUID id);
    
    /**
     * Get all escrow accounts for an order
     * 
     * @param orderId Order ID
     * @return List of escrow accounts
     */
    List<EscrowAccountResponse> getEscrowAccountsByOrderId(Long orderId);
    
    /**
     * Get all escrow accounts for a vendor
     * 
     * @param vendorId Vendor ID
     * @return List of escrow accounts
     */
    List<EscrowAccountResponse> getEscrowAccountsByVendorId(Long vendorId);
    
    /**
     * Get escrow account by transaction ID
     * 
     * @param transactionId Transaction ID
     * @return Escrow account details
     */
    EscrowAccountResponse getEscrowAccountByTransactionId(String transactionId);
    
    /**
     * Update escrow account status
     * 
     * @param id Escrow account ID
     * @param status New status
     * @return Updated escrow account
     */
    EscrowAccountResponse updateEscrowStatus(UUID id, EscrowStatus status);
    
    /**
     * Release funds from escrow
     * 
     * @param id Escrow account ID
     * @param confirmedBy User or system that confirmed the release
     * @param releaseNote Note about the release
     * @return Updated escrow account
     */
    EscrowAccountResponse releaseFunds(UUID id, String confirmedBy, String releaseNote);
    
    /**
     * Release partial funds from escrow
     * 
     * @param id Escrow account ID
     * @param amount Amount to release
     * @param confirmedBy User or system that confirmed the release
     * @param releaseNote Note about the release
     * @return Updated escrow account
     */
    EscrowAccountResponse releasePartialFunds(UUID id, BigDecimal amount, String confirmedBy, String releaseNote);
    
    /**
     * Refund funds from escrow back to customer
     * 
     * @param id Escrow account ID
     * @param confirmedBy User or system that confirmed the refund
     * @param releaseNote Note about the refund
     * @return Updated escrow account
     */
    EscrowAccountResponse refundFunds(UUID id, String confirmedBy, String releaseNote);
    
    /**
     * Get all escrow accounts that are due for auto-release
     * 
     * @return List of escrow accounts due for auto-release
     */
    List<EscrowAccountResponse> getEscrowAccountsDueForAutoRelease();
} 
