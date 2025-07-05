package com.winnguyen1905.payment.core.controller;

import java.math.BigDecimal;
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

import com.winnguyen1905.payment.core.model.request.EscrowAccountRequest;
import com.winnguyen1905.payment.core.model.response.EscrowAccountResponse;
import com.winnguyen1905.payment.core.service.EscrowAccountService;
import com.winnguyen1905.payment.persistance.entity.EscrowAccount.EscrowStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/escrow")
public class EscrowAccountController {

    private final EscrowAccountService escrowAccountService;

    @Autowired
    public EscrowAccountController(EscrowAccountService escrowAccountService) {
        this.escrowAccountService = escrowAccountService;
    }

    @PostMapping
    public ResponseEntity<EscrowAccountResponse> createEscrowAccount(@Valid @RequestBody EscrowAccountRequest request) {
        EscrowAccountResponse response = escrowAccountService.createEscrowAccount(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscrowAccountResponse> getEscrowAccount(@PathVariable UUID id) {
        EscrowAccountResponse response = escrowAccountService.getEscrowAccountById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<EscrowAccountResponse>> getEscrowAccountsByOrderId(@PathVariable Long orderId) {
        List<EscrowAccountResponse> response = escrowAccountService.getEscrowAccountsByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<EscrowAccountResponse>> getEscrowAccountsByVendorId(@PathVariable Long vendorId) {
        List<EscrowAccountResponse> response = escrowAccountService.getEscrowAccountsByVendorId(vendorId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<EscrowAccountResponse> getEscrowAccountByTransactionId(@PathVariable String transactionId) {
        EscrowAccountResponse response = escrowAccountService.getEscrowAccountByTransactionId(transactionId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EscrowAccountResponse> updateEscrowStatus(
            @PathVariable UUID id,
            @RequestParam EscrowStatus status) {
        EscrowAccountResponse response = escrowAccountService.updateEscrowStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<EscrowAccountResponse> releaseFunds(
            @PathVariable UUID id,
            @RequestParam String confirmedBy,
            @RequestParam(required = false) String releaseNote) {
        EscrowAccountResponse response = escrowAccountService.releaseFunds(id, confirmedBy, releaseNote);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/partial-release")
    public ResponseEntity<EscrowAccountResponse> releasePartialFunds(
            @PathVariable UUID id,
            @RequestParam BigDecimal amount,
            @RequestParam String confirmedBy,
            @RequestParam(required = false) String releaseNote) {
        EscrowAccountResponse response = escrowAccountService.releasePartialFunds(id, amount, confirmedBy, releaseNote);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/refund")
    public ResponseEntity<EscrowAccountResponse> refundFunds(
            @PathVariable UUID id,
            @RequestParam String confirmedBy,
            @RequestParam(required = false) String releaseNote) {
        EscrowAccountResponse response = escrowAccountService.refundFunds(id, confirmedBy, releaseNote);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/due-for-release")
    public ResponseEntity<List<EscrowAccountResponse>> getEscrowAccountsDueForAutoRelease() {
        List<EscrowAccountResponse> response = escrowAccountService.getEscrowAccountsDueForAutoRelease();
        return ResponseEntity.ok(response);
    }
} 
