package com.winnguyen1905.payment.core.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.payment.core.model.request.CommissionRuleRequest;
import com.winnguyen1905.payment.core.model.response.CommissionRuleResponse;
import com.winnguyen1905.payment.core.service.CommissionRuleService;
import com.winnguyen1905.payment.exception.BadRequestException;
import com.winnguyen1905.payment.exception.ResourceNotFoundException;
import com.winnguyen1905.payment.persistance.entity.CommissionRule;
import com.winnguyen1905.payment.persistance.entity.CommissionRule.RuleType;
import com.winnguyen1905.payment.persistance.repository.CommissionRuleRepository;

/**
 * Implementation of CommissionRuleService for managing commission rules and calculations.
 */
@Service
@Transactional
public class CommissionRuleServiceImpl implements CommissionRuleService {

    @Autowired
    private CommissionRuleRepository commissionRuleRepository;

    @Override
    public CommissionRuleResponse createCommissionRule(CommissionRuleRequest request) {
        // Check for conflicting rules
        if (hasConflictingRules(request.getVendorId(), request.getCategoryId(), 
                request.getEffectiveDate(), request.getExpirationDate(), null)) {
            throw new BadRequestException("Conflicting commission rule already exists for the specified vendor/category and date range");
        }
        
        CommissionRule commissionRule = CommissionRule.builder()
            .vendorId(request.getVendorId())
            .categoryId(request.getCategoryId())
            .ruleName(request.getRuleName())
            .ruleType(request.getRuleType())
            .commissionRate(request.getCommissionRate())
            .fixedFeeAmount(request.getFixedFeeAmount())
            .currency(request.getCurrency())
            .minCommissionAmount(request.getMinCommissionAmount())
            .maxCommissionAmount(request.getMaxCommissionAmount())
            .tierStructure(request.getTierStructure())
            .effectiveDate(request.getEffectiveDate())
            .expirationDate(request.getExpirationDate())
            .isActive(request.getIsActive() != null ? request.getIsActive() : true)
            .priority(request.getPriority() != null ? request.getPriority() : 0)
            .description(request.getDescription())
            .additionalFees(request.getAdditionalFees())
            .build();
        
        commissionRule = commissionRuleRepository.save(commissionRule);
        return mapToResponse(commissionRule);
    }

    @Override
    @Transactional(readOnly = true)
    public CommissionRuleResponse getCommissionRuleById(UUID id) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        return mapToResponse(commissionRule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getAllCommissionRules() {
        List<CommissionRule> commissionRules = commissionRuleRepository.findAll();
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getCommissionRulesByVendorId(Long vendorId) {
        List<CommissionRule> commissionRules = commissionRuleRepository.findByVendorId(vendorId);
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getCommissionRulesByCategoryId(Long categoryId) {
        List<CommissionRule> commissionRules = commissionRuleRepository.findByCategoryId(categoryId);
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getCommissionRulesByRuleType(RuleType ruleType) {
        List<CommissionRule> commissionRules = commissionRuleRepository.findByRuleType(ruleType);
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getActiveCommissionRules() {
        List<CommissionRule> commissionRules = commissionRuleRepository.findByIsActiveTrue();
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getCommissionRulesEffectiveAt(LocalDateTime effectiveDate) {
        List<CommissionRule> commissionRules = commissionRuleRepository.findEffectiveRulesAt(effectiveDate);
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getCommissionRulesByVendorAndCategory(Long vendorId, Long categoryId) {
        List<CommissionRule> commissionRules = commissionRuleRepository.findByVendorIdAndCategoryId(vendorId, categoryId);
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CommissionRuleResponse getBestApplicableCommissionRule(Long vendorId, Long categoryId, 
            BigDecimal orderAmount, LocalDateTime effectiveDate) {
        List<CommissionRule> applicableRules = commissionRuleRepository.findBestApplicableRules(
            vendorId, categoryId, effectiveDate);
        
        if (applicableRules.isEmpty()) {
            throw new ResourceNotFoundException("No applicable commission rule found for vendor: " + vendorId);
        }
        
        // Return the first rule (highest priority)
        return mapToResponse(applicableRules.get(0));
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCommission(Long vendorId, Long categoryId, BigDecimal orderAmount, LocalDateTime effectiveDate) {
        List<CommissionRule> applicableRules = commissionRuleRepository.findBestApplicableRules(
            vendorId, categoryId, effectiveDate);
        
        if (applicableRules.isEmpty()) {
            throw new ResourceNotFoundException("No applicable commission rule found for vendor: " + vendorId);
        }
        
        CommissionRule rule = applicableRules.get(0);
        return calculateCommissionAmount(rule, orderAmount);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateCommissionByRule(UUID ruleId, BigDecimal orderAmount) {
        CommissionRule rule = commissionRuleRepository.findById(ruleId)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + ruleId));
        
        return calculateCommissionAmount(rule, orderAmount);
    }

    @Override
    public CommissionRuleResponse updateCommissionRule(UUID id, CommissionRuleRequest request) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        
        // Check for conflicting rules (excluding current rule)
        if (hasConflictingRules(request.getVendorId(), request.getCategoryId(), 
                request.getEffectiveDate(), request.getExpirationDate(), id)) {
            throw new BadRequestException("Conflicting commission rule already exists for the specified vendor/category and date range");
        }
        
        commissionRule.setVendorId(request.getVendorId());
        commissionRule.setCategoryId(request.getCategoryId());
        commissionRule.setRuleName(request.getRuleName());
        commissionRule.setRuleType(request.getRuleType());
        commissionRule.setCommissionRate(request.getCommissionRate());
        commissionRule.setFixedFeeAmount(request.getFixedFeeAmount());
        commissionRule.setCurrency(request.getCurrency());
        commissionRule.setMinCommissionAmount(request.getMinCommissionAmount());
        commissionRule.setMaxCommissionAmount(request.getMaxCommissionAmount());
        commissionRule.setTierStructure(request.getTierStructure());
        commissionRule.setEffectiveDate(request.getEffectiveDate());
        commissionRule.setExpirationDate(request.getExpirationDate());
        commissionRule.setIsActive(request.getIsActive());
        commissionRule.setPriority(request.getPriority());
        commissionRule.setDescription(request.getDescription());
        commissionRule.setAdditionalFees(request.getAdditionalFees());
        
        commissionRule = commissionRuleRepository.save(commissionRule);
        return mapToResponse(commissionRule);
    }

    @Override
    public CommissionRuleResponse activateCommissionRule(UUID id) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        
        commissionRule.setIsActive(true);
        commissionRule = commissionRuleRepository.save(commissionRule);
        return mapToResponse(commissionRule);
    }

    @Override
    public CommissionRuleResponse deactivateCommissionRule(UUID id) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        
        commissionRule.setIsActive(false);
        commissionRule = commissionRuleRepository.save(commissionRule);
        return mapToResponse(commissionRule);
    }

    @Override
    public CommissionRuleResponse updatePriority(UUID id, Integer priority) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        
        commissionRule.setPriority(priority);
        commissionRule = commissionRuleRepository.save(commissionRule);
        return mapToResponse(commissionRule);
    }

    @Override
    public CommissionRuleResponse updateEffectiveDates(UUID id, LocalDateTime effectiveDate, LocalDateTime expirationDate) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        
        // Check for conflicting rules with new dates
        if (hasConflictingRules(commissionRule.getVendorId(), commissionRule.getCategoryId(), 
                effectiveDate, expirationDate, id)) {
            throw new BadRequestException("Conflicting commission rule already exists for the specified date range");
        }
        
        commissionRule.setEffectiveDate(effectiveDate);
        commissionRule.setExpirationDate(expirationDate);
        commissionRule = commissionRuleRepository.save(commissionRule);
        return mapToResponse(commissionRule);
    }

    @Override
    public CommissionRuleResponse updateCommissionRate(UUID id, BigDecimal commissionRate) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        
        commissionRule.setCommissionRate(commissionRate);
        commissionRule = commissionRuleRepository.save(commissionRule);
        return mapToResponse(commissionRule);
    }

    @Override
    public void deleteCommissionRule(UUID id) {
        CommissionRule commissionRule = commissionRuleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Commission rule not found with ID: " + id));
        
        commissionRuleRepository.delete(commissionRule);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasConflictingRules(Long vendorId, Long categoryId, LocalDateTime effectiveDate, 
            LocalDateTime expirationDate, UUID excludeRuleId) {
        return commissionRuleRepository.existsConflictingRules(vendorId, categoryId, effectiveDate, expirationDate, excludeRuleId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommissionRuleResponse> getCommissionRulesExpiringSoon(LocalDateTime beforeDate) {
        List<CommissionRule> commissionRules = commissionRuleRepository.findRulesExpiringSoon(beforeDate);
        return commissionRules.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Calculate commission amount based on rule and order amount
     */
    private BigDecimal calculateCommissionAmount(CommissionRule rule, BigDecimal orderAmount) {
        BigDecimal commission = BigDecimal.ZERO;
        
        switch (rule.getRuleType()) {
            case FLAT_PERCENTAGE:
                commission = orderAmount.multiply(rule.getCommissionRate())
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                break;
                
            case FLAT_FEE:
                commission = rule.getFixedFeeAmount() != null ? rule.getFixedFeeAmount() : BigDecimal.ZERO;
                break;
                
            case PERCENTAGE_PLUS_FEE:
                BigDecimal percentageCommission = orderAmount.multiply(rule.getCommissionRate())
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                BigDecimal fixedFee = rule.getFixedFeeAmount() != null ? rule.getFixedFeeAmount() : BigDecimal.ZERO;
                commission = percentageCommission.add(fixedFee);
                break;
                
            case TIERED_PERCENTAGE:
            case CATEGORY_SPECIFIC:
            case VOLUME_BASED:
                // For complex calculations, use the base percentage for now
                // In a real implementation, you would parse tierStructure JSON
                commission = orderAmount.multiply(rule.getCommissionRate())
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                break;
                
            default:
                throw new BadRequestException("Unsupported commission rule type: " + rule.getRuleType());
        }
        
        // Apply min/max commission limits
        if (rule.getMinCommissionAmount() != null && commission.compareTo(rule.getMinCommissionAmount()) < 0) {
            commission = rule.getMinCommissionAmount();
        }
        
        if (rule.getMaxCommissionAmount() != null && commission.compareTo(rule.getMaxCommissionAmount()) > 0) {
            commission = rule.getMaxCommissionAmount();
        }
        
        return commission.setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * Maps CommissionRule entity to CommissionRuleResponse DTO
     */
    private CommissionRuleResponse mapToResponse(CommissionRule commissionRule) {
        return CommissionRuleResponse.builder()
            .id(commissionRule.getId())
            .vendorId(commissionRule.getVendorId())
            .categoryId(commissionRule.getCategoryId())
            .ruleName(commissionRule.getRuleName())
            .ruleType(commissionRule.getRuleType())
            .commissionRate(commissionRule.getCommissionRate())
            .fixedFeeAmount(commissionRule.getFixedFeeAmount())
            .currency(commissionRule.getCurrency())
            .minCommissionAmount(commissionRule.getMinCommissionAmount())
            .maxCommissionAmount(commissionRule.getMaxCommissionAmount())
            .tierStructure(commissionRule.getTierStructure())
            .effectiveDate(commissionRule.getEffectiveDate())
            .expirationDate(commissionRule.getExpirationDate())
            .isActive(commissionRule.getIsActive())
            .priority(commissionRule.getPriority())
            .description(commissionRule.getDescription())
            .additionalFees(commissionRule.getAdditionalFees())
            .createdAt(commissionRule.getCreatedAt())
            .updatedAt(commissionRule.getUpdatedAt())
            .build();
    }
} 
