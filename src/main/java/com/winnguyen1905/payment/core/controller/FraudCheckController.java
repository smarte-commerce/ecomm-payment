package com.winnguyen1905.payment.core.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.winnguyen1905.payment.core.model.response.RestResponse;
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
    public RestResponse<FraudCheckResponse> createFraudCheck(@Valid @RequestBody FraudCheckRequest request) {
        FraudCheckResponse response = fraudCheckService.createFraudCheck(request);
        return RestResponse.<FraudCheckResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Fraud check created successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public RestResponse<FraudCheckResponse> getFraudCheck(@PathVariable UUID id) {
        FraudCheckResponse response = fraudCheckService.getFraudCheckById(id);
        return RestResponse.<FraudCheckResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/payment/{paymentId}")
    public RestResponse<List<FraudCheckResponse>> getFraudChecksByPaymentId(@PathVariable UUID paymentId) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByPaymentId(paymentId);
        return RestResponse.<List<FraudCheckResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public RestResponse<List<FraudCheckResponse>> getFraudChecksByOrderId(@PathVariable Long orderId) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByOrderId(orderId);
        return RestResponse.<List<FraudCheckResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public RestResponse<List<FraudCheckResponse>> getFraudChecksByCustomerId(@PathVariable Long customerId) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByCustomerId(customerId);
        return RestResponse.<List<FraudCheckResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/status/{status}")
    public RestResponse<List<FraudCheckResponse>> getFraudChecksByStatus(@PathVariable CheckStatus status) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByStatus(status);
        return RestResponse.<List<FraudCheckResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/risk-level/{riskLevel}")
    public RestResponse<List<FraudCheckResponse>> getFraudChecksByRiskLevel(@PathVariable RiskLevel riskLevel) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByRiskLevel(riskLevel);
        return RestResponse.<List<FraudCheckResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/check-type/{checkType}")
    public RestResponse<List<FraudCheckResponse>> getFraudChecksByCheckType(@PathVariable CheckType checkType) {
        List<FraudCheckResponse> response = fraudCheckService.getFraudChecksByCheckType(checkType);
        return RestResponse.<List<FraudCheckResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PutMapping("/{id}/status")
    public RestResponse<FraudCheckResponse> updateFraudCheckStatus(
            @PathVariable UUID id,
            @RequestParam CheckStatus status) {
        FraudCheckResponse response = fraudCheckService.updateFraudCheckStatus(id, status);
        return RestResponse.<FraudCheckResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Fraud check status updated successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/review")
    public RestResponse<FraudCheckResponse> addReviewerDecision(
            @PathVariable UUID id,
            @RequestParam Long reviewerId,
            @RequestParam(required = false) String reviewerNotes,
            @RequestParam(required = false) String decisionReason) {
        FraudCheckResponse response = fraudCheckService.addReviewerDecision(id, reviewerId, reviewerNotes, decisionReason);
        return RestResponse.<FraudCheckResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Reviewer decision added successfully")
                .data(response)
                .build();
    }

    @GetMapping("/pending/risk-levels")
    public RestResponse<List<FraudCheckResponse>> getPendingFraudChecksByRiskLevels(
            @RequestParam List<RiskLevel> riskLevels) {
        List<FraudCheckResponse> response = fraudCheckService.getPendingFraudChecksByRiskLevels(riskLevels);
        return RestResponse.<List<FraudCheckResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/customer/{customerId}/average-risk-score")
    public RestResponse<Double> getAverageRiskScoreByCustomerId(@PathVariable Long customerId) {
        Double averageRiskScore = fraudCheckService.getAverageRiskScoreByCustomerId(customerId);
        return RestResponse.<Double>builder()
                .statusCode(HttpStatus.OK.value())
                .data(averageRiskScore)
                .build();
    }
} 
