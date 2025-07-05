package com.winnguyen1905.payment.core.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.CommissionRuleRequest;
import com.winnguyen1905.payment.core.model.response.CommissionRuleResponse;
import com.winnguyen1905.payment.persistance.entity.CommissionRule.RuleType;

/**
 * Service interface for managing commission rules and fee structures.
 */
public interface CommissionRuleService {
    
    /**
     * Create a new commission rule
     * 
     * @param request Commission rule details
     * @return Created commission rule
     */
    CommissionRuleResponse createCommissionRule(CommissionRuleRequest request);
    
    /**
     * Get a commission rule by ID
     * 
     * @param id Commission rule ID
     * @return Commission rule details
     */
    CommissionRuleResponse getCommissionRuleById(UUID id);
    
    /**
     * Get all commission rules
     * 
     * @return List of commission rules
     */
    List<CommissionRuleResponse> getAllCommissionRules();
    
    /**
     * Get commission rules by vendor ID
     * 
     * @param vendorId Vendor ID
     * @return List of commission rules for the vendor
     */
    List<CommissionRuleResponse> getCommissionRulesByVendorId(Long vendorId);
    
    /**
     * Get commission rules by category ID
     * 
     * @param categoryId Category ID
     * @return List of commission rules for the category
     */
    List<CommissionRuleResponse> getCommissionRulesByCategoryId(Long categoryId);
    
    /**
     * Get commission rules by rule type
     * 
     * @param ruleType Rule type
     * @return List of commission rules of the specified type
     */
    List<CommissionRuleResponse> getCommissionRulesByRuleType(RuleType ruleType);
    
    /**
     * Get active commission rules
     * 
     * @return List of active commission rules
     */
    List<CommissionRuleResponse> getActiveCommissionRules();
    
    /**
     * Get commission rules effective at a specific date
     * 
     * @param effectiveDate Date to check
     * @return List of commission rules effective at the date
     */
    List<CommissionRuleResponse> getCommissionRulesEffectiveAt(LocalDateTime effectiveDate);
    
    /**
     * Get commission rules by vendor and category
     * 
     * @param vendorId Vendor ID
     * @param categoryId Category ID
     * @return List of commission rules for vendor and category
     */
    List<CommissionRuleResponse> getCommissionRulesByVendorAndCategory(Long vendorId, Long categoryId);
    
    /**
     * Get the best applicable commission rule for calculation
     * 
     * @param vendorId Vendor ID
     * @param categoryId Category ID (optional)
     * @param orderAmount Order amount
     * @param effectiveDate Effective date
     * @return Best applicable commission rule
     */
    CommissionRuleResponse getBestApplicableCommissionRule(Long vendorId, Long categoryId, BigDecimal orderAmount, LocalDateTime effectiveDate);
    
    /**
     * Calculate commission based on the best applicable rule
     * 
     * @param vendorId Vendor ID
     * @param categoryId Category ID (optional)
     * @param orderAmount Order amount
     * @param effectiveDate Effective date
     * @return Calculated commission amount
     */
    BigDecimal calculateCommission(Long vendorId, Long categoryId, BigDecimal orderAmount, LocalDateTime effectiveDate);
    
    /**
     * Calculate commission using a specific rule
     * 
     * @param ruleId Commission rule ID
     * @param orderAmount Order amount
     * @return Calculated commission amount
     */
    BigDecimal calculateCommissionByRule(UUID ruleId, BigDecimal orderAmount);
    
    /**
     * Update commission rule
     * 
     * @param id Commission rule ID
     * @param request Updated commission rule details
     * @return Updated commission rule
     */
    CommissionRuleResponse updateCommissionRule(UUID id, CommissionRuleRequest request);
    
    /**
     * Activate commission rule
     * 
     * @param id Commission rule ID
     * @return Updated commission rule
     */
    CommissionRuleResponse activateCommissionRule(UUID id);
    
    /**
     * Deactivate commission rule
     * 
     * @param id Commission rule ID
     * @return Updated commission rule
     */
    CommissionRuleResponse deactivateCommissionRule(UUID id);
    
    /**
     * Update commission rule priority
     * 
     * @param id Commission rule ID
     * @param priority New priority
     * @return Updated commission rule
     */
    CommissionRuleResponse updatePriority(UUID id, Integer priority);
    
    /**
     * Update commission rule effective dates
     * 
     * @param id Commission rule ID
     * @param effectiveDate New effective date
     * @param expirationDate New expiration date (optional)
     * @return Updated commission rule
     */
    CommissionRuleResponse updateEffectiveDates(UUID id, LocalDateTime effectiveDate, LocalDateTime expirationDate);
    
    /**
     * Update commission rate
     * 
     * @param id Commission rule ID
     * @param commissionRate New commission rate
     * @return Updated commission rule
     */
    CommissionRuleResponse updateCommissionRate(UUID id, BigDecimal commissionRate);
    
    /**
     * Delete commission rule
     * 
     * @param id Commission rule ID
     */
    void deleteCommissionRule(UUID id);
    
    /**
     * Check if commission rule conflicts with existing rules
     * 
     * @param vendorId Vendor ID
     * @param categoryId Category ID (optional)
     * @param effectiveDate Effective date
     * @param expirationDate Expiration date (optional)
     * @param excludeRuleId Rule ID to exclude from conflict check (for updates)
     * @return True if there are conflicts
     */
    boolean hasConflictingRules(Long vendorId, Long categoryId, LocalDateTime effectiveDate, LocalDateTime expirationDate, UUID excludeRuleId);
    
    /**
     * Get commission rules expiring soon
     * 
     * @param beforeDate Date before which rules expire
     * @return List of commission rules expiring soon
     */
    List<CommissionRuleResponse> getCommissionRulesExpiringSoon(LocalDateTime beforeDate);
} 
