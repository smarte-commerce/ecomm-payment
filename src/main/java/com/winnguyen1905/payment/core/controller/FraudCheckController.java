package com.winnguyen1905.payment.core.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winnguyen1905.payment.core.model.request.FraudCheckRequest;
import com.winnguyen1905.payment.core.model.response.FraudCheckResponse;
import com.winnguyen1905.payment.core.service.FraudCheckService;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckStatus;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckType;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.RiskLevel;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/fraud-checks")
public class FraudCheckController {

    private final FraudCheckService fraudCheckService;

    @Autowired
    public FraudCheckController(FraudCheckService fraudCheckService) {
        this.fraudCheckService = fraudCheckService;
    }

    @PostMapping
    public ResponseEntity<FraudCheckResponse> createFraudCheck(@Valid @RequestBody FraudCheckRequest request) {
        FraudCheckResponse response = fraudCheckService.createFraudCheck(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FraudCheckResponse> getFraudCheck(@PathVariable UUID id) {
        FraudCheckResponse response = fraudCheckService.getFraudCheckById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<FraudCheckResponse>> getFraudChecksByPaymentId(@PathVariable UUID paymentId) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByPaymentId(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<FraudCheckResponse>> getFraudChecksByOrderId(@PathVariable Long orderId) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<FraudCheckResponse>> getFraudChecksByCustomerId(@PathVariable Long customerId) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<FraudCheckResponse>> getFraudChecksByStatus(@PathVariable CheckStatus status) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/risk-level/{riskLevel}")
    public ResponseEntity<List<FraudCheckResponse>> getFraudChecksByRiskLevel(@PathVariable RiskLevel riskLevel) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByRiskLevel(riskLevel);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-type/{checkType}")
    public ResponseEntity<List<FraudCheckResponse>> getFraudChecksByCheckType(@PathVariable CheckType checkType) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByCheckType(checkType);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<FraudCheckResponse> updateFraudCheckStatus(
            @PathVariable UUID id,
            @RequestParam CheckStatus status) {
        FraudCheckResponse response = fraudCheckService.updateFraudCheckStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/review")
    public ResponseEntity<FraudCheckResponse> addReviewerDecision(
            @PathVariable UUID id,
            @RequestParam Long reviewerId,
            @RequestParam(required = false) String reviewerNotes,
            @RequestParam(required = false) String decisionReason) {
        FraudCheckResponse response = fraudCheckService.addReviewerDecision(id, reviewerId, reviewerNotes, decisionReason);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pending/risk-levels")
    public ResponseEntity<List<FraudCheckResponse>> getPendingFraudChecksByRiskLevels(
            @RequestParam List<RiskLevel> riskLevels) {
        List<FraudCheckResponse> response = fraudCheckService.getPendingFraudChecksByRiskLevels(riskLevels);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/average-risk-score")
    public ResponseEntity<Double> getAverageRiskScoreByCustomerId(@PathVariable Long customerId) {
        Double averageRiskScore = fraudCheckService.getAverageRiskScoreByCustomerId(customerId);
        return ResponseEntity.ok(averageRiskScore);
    }
} 
