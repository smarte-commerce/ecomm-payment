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

import com.winnguyen1905.payment.core.model.request.RefundRequest;
import com.winnguyen1905.payment.core.model.response.RefundResponse;
import com.winnguyen1905.payment.core.service.RefundService;
import com.winnguyen1905.payment.persistance.entity.ERefund.RefundStatus;
import com.winnguyen1905.payment.persistance.entity.ERefund.RefundReason;

import jakarta.validation.Valid;

/**
 * Controller for managing refunds and refund processing.
 * Handles refund creation, approval workflows, and status updates.
 */
@RestController
@RequestMapping("/api/v1/refunds")
public class RefundController {

    private final RefundService refundService;

    @Autowired
    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping
    public ResponseEntity<RefundResponse> createRefund(@Valid @RequestBody RefundRequest request) {
        RefundResponse response = refundService.createRefund(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RefundResponse> getRefund(@PathVariable UUID id) {
        RefundResponse response = refundService.getRefundById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refund-number/{refundNumber}")
    public ResponseEntity<RefundResponse> getRefundByNumber(@PathVariable String refundNumber) {
        RefundResponse response = refundService.getRefundByRefundNumber(refundNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<RefundResponse>> getRefundsByPaymentId(@PathVariable UUID paymentId) {
        List<RefundResponse> response = refundService.getRefundsByPaymentId(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<RefundResponse>> getRefundsByOrderId(@PathVariable Long orderId) {
        List<RefundResponse> response = refundService.getRefundsByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<RefundResponse>> getRefundsByStatus(@PathVariable RefundStatus status) {
        List<RefundResponse> response = refundService.getRefundsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provider-refund/{providerRefundId}")
    public ResponseEntity<RefundResponse> getRefundByProviderRefundId(@PathVariable String providerRefundId) {
        RefundResponse response = refundService.getRefundByProviderRefundId(providerRefundId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<RefundResponse> updateRefundStatus(
            @PathVariable UUID id,
            @RequestParam RefundStatus status) {
        RefundResponse response = refundService.updateRefundStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<RefundResponse> processRefund(@PathVariable UUID id) {
        RefundResponse response = refundService.processRefund(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<RefundResponse> completeRefund(@PathVariable UUID id) {
        RefundResponse response = refundService.completeRefund(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/fail")
    public ResponseEntity<RefundResponse> failRefund(
            @PathVariable UUID id,
            @RequestParam String failureReason) {
        RefundResponse response = refundService.failRefund(id, failureReason);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<RefundResponse> cancelRefund(@PathVariable UUID id) {
        RefundResponse response = refundService.cancelRefund(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/gateway-response")
    public ResponseEntity<RefundResponse> updateGatewayResponse(
            @PathVariable UUID id,
            @RequestParam String gatewayResponse) {
        RefundResponse response = refundService.updateGatewayResponse(id, gatewayResponse);
        return ResponseEntity.ok(response);
    }
} 
