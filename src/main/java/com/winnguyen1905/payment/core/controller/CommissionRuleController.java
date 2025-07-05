package com.winnguyen1905.payment.core.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winnguyen1905.payment.core.model.request.CommissionRuleRequest;
import com.winnguyen1905.payment.core.model.response.CommissionRuleResponse;
import com.winnguyen1905.payment.core.service.CommissionRuleService;
import com.winnguyen1905.payment.persistance.entity.CommissionRule.RuleType;

import jakarta.validation.Valid;

/**
 * Controller for managing commission rules and fee structures.
 * Handles commission rule creation, calculation logic, and rule management.
 */
@RestController
@RequestMapping("/api/v1/commission-rules")
public class CommissionRuleController {

    private final CommissionRuleService commissionRuleService;

    @Autowired
    public CommissionRuleController(CommissionRuleService commissionRuleService) {
        this.commissionRuleService = commissionRuleService;
    }

    @PostMapping
    public ResponseEntity<CommissionRuleResponse> createCommissionRule(@Valid @RequestBody CommissionRuleRequest request) {
        CommissionRuleResponse response = commissionRuleService.createCommissionRule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommissionRuleResponse> getCommissionRule(@PathVariable UUID id) {
        CommissionRuleResponse response = commissionRuleService.getCommissionRuleById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CommissionRuleResponse>> getAllCommissionRules() {
        List<CommissionRuleResponse> response = commissionRuleService.getAllCommissionRules();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<CommissionRuleResponse>> getCommissionRulesByVendorId(@PathVariable Long vendorId) {
        List<CommissionRuleResponse> response = commissionRuleService.getCommissionRulesByVendorId(vendorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CommissionRuleResponse>> getCommissionRulesByCategoryId(@PathVariable Long categoryId) {
        List<CommissionRuleResponse> response = commissionRuleService.getCommissionRulesByCategoryId(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/rule-type/{ruleType}")
    public ResponseEntity<List<CommissionRuleResponse>> getCommissionRulesByRuleType(@PathVariable RuleType ruleType) {
        List<CommissionRuleResponse> response = commissionRuleService.getCommissionRulesByRuleType(ruleType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<List<CommissionRuleResponse>> getActiveCommissionRules() {
        List<CommissionRuleResponse> response = commissionRuleService.getActiveCommissionRules();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/effective-at")
    public ResponseEntity<List<CommissionRuleResponse>> getCommissionRulesEffectiveAt(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime effectiveDate) {
        List<CommissionRuleResponse> response = commissionRuleService.getCommissionRulesEffectiveAt(effectiveDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}/category/{categoryId}")
    public ResponseEntity<List<CommissionRuleResponse>> getCommissionRulesByVendorAndCategory(
            @PathVariable Long vendorId,
            @PathVariable Long categoryId) {
        List<CommissionRuleResponse> response = commissionRuleService.getCommissionRulesByVendorAndCategory(vendorId, categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/best-applicable")
    public ResponseEntity<CommissionRuleResponse> getBestApplicableCommissionRule(
            @RequestParam Long vendorId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam BigDecimal orderAmount,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime effectiveDate) {
        CommissionRuleResponse response = commissionRuleService.getBestApplicableCommissionRule(vendorId, categoryId, orderAmount, effectiveDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calculate")
    public ResponseEntity<BigDecimal> calculateCommission(
            @RequestParam Long vendorId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam BigDecimal orderAmount,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime effectiveDate) {
        BigDecimal commission = commissionRuleService.calculateCommission(vendorId, categoryId, orderAmount, effectiveDate);
        return ResponseEntity.ok(commission);
    }

    @GetMapping("/{ruleId}/calculate")
    public ResponseEntity<BigDecimal> calculateCommissionByRule(
            @PathVariable UUID ruleId,
            @RequestParam BigDecimal orderAmount) {
        BigDecimal commission = commissionRuleService.calculateCommissionByRule(ruleId, orderAmount);
        return ResponseEntity.ok(commission);
    }

    @GetMapping("/expiring-soon")
    public ResponseEntity<List<CommissionRuleResponse>> getCommissionRulesExpiringSoon(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime beforeDate) {
        List<CommissionRuleResponse> response = commissionRuleService.getCommissionRulesExpiringSoon(beforeDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommissionRuleResponse> updateCommissionRule(
            @PathVariable UUID id,
            @Valid @RequestBody CommissionRuleRequest request) {
        CommissionRuleResponse response = commissionRuleService.updateCommissionRule(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<CommissionRuleResponse> activateCommissionRule(@PathVariable UUID id) {
        CommissionRuleResponse response = commissionRuleService.activateCommissionRule(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<CommissionRuleResponse> deactivateCommissionRule(@PathVariable UUID id) {
        CommissionRuleResponse response = commissionRuleService.deactivateCommissionRule(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/priority")
    public ResponseEntity<CommissionRuleResponse> updatePriority(
            @PathVariable UUID id,
            @RequestParam Integer priority) {
        CommissionRuleResponse response = commissionRuleService.updatePriority(id, priority);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/effective-dates")
    public ResponseEntity<CommissionRuleResponse> updateEffectiveDates(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime effectiveDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expirationDate) {
        CommissionRuleResponse response = commissionRuleService.updateEffectiveDates(id, effectiveDate, expirationDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/commission-rate")
    public ResponseEntity<CommissionRuleResponse> updateCommissionRate(
            @PathVariable UUID id,
            @RequestParam BigDecimal commissionRate) {
        CommissionRuleResponse response = commissionRuleService.updateCommissionRate(id, commissionRate);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-conflicts")
    public ResponseEntity<Boolean> checkConflictingRules(
            @RequestParam Long vendorId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime effectiveDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime expirationDate,
            @RequestParam(required = false) UUID excludeRuleId) {
        boolean hasConflicts = commissionRuleService.hasConflictingRules(vendorId, categoryId, effectiveDate, expirationDate, excludeRuleId);
        return ResponseEntity.ok(hasConflicts);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommissionRule(@PathVariable UUID id) {
        commissionRuleService.deleteCommissionRule(id);
        return ResponseEntity.noContent().build();
    }
} 
