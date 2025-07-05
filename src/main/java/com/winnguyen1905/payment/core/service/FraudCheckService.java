package com.winnguyen1905.payment.core.service;

import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.FraudCheckRequest;
import com.winnguyen1905.payment.core.model.response.FraudCheckResponse;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckStatus;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckType;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.RiskLevel;

/**
 * Service interface for managing fraud checks and risk assessment.
 */
public interface FraudCheckService {
    
    /**
     * Create a new fraud check
     * 
     * @param request Fraud check details
     * @return Created fraud check
     */
    FraudCheckResponse createFraudCheck(FraudCheckRequest request);
    
    /**
     * Get a fraud check by ID
     * 
     * @param id Fraud check ID
     * @return Fraud check details
     */
    FraudCheckResponse getFraudCheckById(UUID id);
    
    /**
     * Get all fraud checks for a payment
     * 
     * @param paymentId Payment ID
     * @return List of fraud checks
     */
    List<FraudCheckResponse> getFraudChecksByPaymentId(UUID paymentId);
    
    /**
     * Get all fraud checks for an order
     * 
     * @param orderId Order ID
     * @return List of fraud checks
     */
    List<FraudCheckResponse> getFraudChecksByOrderId(Long orderId);
    
    /**
     * Get all fraud checks for a customer
     * 
     * @param customerId Customer ID
     * @return List of fraud checks
     */
    List<FraudCheckResponse> getFraudChecksByCustomerId(Long customerId);
    
    /**
     * Get fraud checks by status
     * 
     * @param status Check status
     * @return List of fraud checks
     */
    List<FraudCheckResponse> getFraudChecksByStatus(CheckStatus status);
    
    /**
     * Get fraud checks by risk level
     * 
     * @param riskLevel Risk level
     * @return List of fraud checks
     */
    List<FraudCheckResponse> getFraudChecksByRiskLevel(RiskLevel riskLevel);
    
    /**
     * Get fraud checks by check type
     * 
     * @param checkType Check type
     * @return List of fraud checks
     */
    List<FraudCheckResponse> getFraudChecksByCheckType(CheckType checkType);
    
    /**
     * Update fraud check status
     * 
     * @param id Fraud check ID
     * @param status New status
     * @return Updated fraud check
     */
    FraudCheckResponse updateFraudCheckStatus(UUID id, CheckStatus status);
    
    /**
     * Add reviewer decision to fraud check
     * 
     * @param id Fraud check ID
     * @param reviewerId Reviewer ID
     * @param reviewerNotes Reviewer notes
     * @param decisionReason Decision reason
     * @return Updated fraud check
     */
    FraudCheckResponse addReviewerDecision(UUID id, Long reviewerId, String reviewerNotes, String decisionReason);
    
    /**
     * Get pending fraud checks by risk levels
     * 
     * @param riskLevels Risk levels to filter
     * @return List of pending fraud checks
     */
    List<FraudCheckResponse> getPendingFraudChecksByRiskLevels(List<RiskLevel> riskLevels);
    
    /**
     * Get average risk score for a customer
     * 
     * @param customerId Customer ID
     * @return Average risk score
     */
    Double getAverageRiskScoreByCustomerId(Long customerId);
} 
