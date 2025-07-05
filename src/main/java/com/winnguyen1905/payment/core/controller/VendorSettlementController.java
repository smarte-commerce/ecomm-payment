package com.winnguyen1905.payment.core.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.winnguyen1905.payment.core.model.request.VendorSettlementRequest;
import com.winnguyen1905.payment.core.model.response.VendorAccountResponse;
import com.winnguyen1905.payment.core.model.response.VendorSettlementInfoResponse;
import com.winnguyen1905.payment.core.model.response.VendorSettlementResponse;
import com.winnguyen1905.payment.core.service.VendorSettlementService;
import com.winnguyen1905.payment.persistance.entity.VendorSettlement.SettlementStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/settlements")
public class VendorSettlementController {

    private final VendorSettlementService vendorSettlementService;

    @Autowired
    public VendorSettlementController(VendorSettlementService vendorSettlementService) {
        this.vendorSettlementService = vendorSettlementService;
    }

    @PostMapping
    public ResponseEntity<VendorSettlementResponse> createSettlement(@Valid @RequestBody VendorSettlementRequest request) {
        VendorSettlementResponse response = vendorSettlementService.createSettlement(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorSettlementResponse> getSettlement(@PathVariable UUID id) {
        VendorSettlementResponse response = vendorSettlementService.getSettlementById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<VendorSettlementResponse>> getSettlementsByVendorId(@PathVariable Long vendorId) {
        List<VendorSettlementResponse> response = vendorSettlementService.getSettlementsByVendorId(vendorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<VendorSettlementResponse>> getSettlementsByOrderId(@PathVariable Long orderId) {
        List<VendorSettlementResponse> response = vendorSettlementService.getSettlementsByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<VendorSettlementResponse> updateSettlementStatus(
            @PathVariable UUID id,
            @RequestParam SettlementStatus status) {
        VendorSettlementResponse response = vendorSettlementService.updateSettlementStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calculate-commission")
    public ResponseEntity<BigDecimal> calculateCommission(
            @RequestParam Long vendorId,
            @RequestParam Long orderId,
            @RequestParam BigDecimal orderAmount) {
        BigDecimal commission = vendorSettlementService.calculateCommission(vendorId, orderId, orderAmount);
        return ResponseEntity.ok(commission);
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<VendorSettlementResponse> processSettlementPayment(@PathVariable UUID id) {
        VendorSettlementResponse response = vendorSettlementService.processSettlementPayment(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/due-for-payment")
    public ResponseEntity<List<VendorSettlementResponse>> getSettlementsDueForPayment(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate) {
        List<VendorSettlementResponse> response = vendorSettlementService.getSettlementsDueForPayment(dueDate);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/notify")
    public ResponseEntity<Boolean> notifyVendorAccountService(@PathVariable UUID id) {
        boolean success = vendorSettlementService.notifyVendorAccountService(id);
        return ResponseEntity.ok(success);
    }

    @GetMapping("/vendor/{vendorId}/account-info")
    public ResponseEntity<VendorAccountResponse> getVendorAccountInfo(@PathVariable Long vendorId) {
        VendorAccountResponse response = vendorSettlementService.getVendorAccountInfo(vendorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}/settlement-info")
    public ResponseEntity<VendorSettlementInfoResponse> getVendorSettlementInfo(@PathVariable Long vendorId) {
        VendorSettlementInfoResponse response = vendorSettlementService.getVendorSettlementInfo(vendorId);
        return ResponseEntity.ok(response);
    }
} 
