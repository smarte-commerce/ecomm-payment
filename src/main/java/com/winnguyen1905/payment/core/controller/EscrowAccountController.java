package com.winnguyen1905.payment.core.controller;

import java.math.BigDecimal;
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

import com.winnguyen1905.payment.core.model.request.EscrowAccountRequest;
import com.winnguyen1905.payment.core.model.response.EscrowAccountResponse;
import com.winnguyen1905.payment.core.model.response.RestResponse;
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
    public RestResponse<EscrowAccountResponse> createEscrowAccount(@Valid @RequestBody EscrowAccountRequest request) {
        EscrowAccountResponse response = escrowAccountService.createEscrowAccount(request);
        return RestResponse.<EscrowAccountResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Escrow account created successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public RestResponse<EscrowAccountResponse> getEscrowAccount(@PathVariable UUID id) {
        EscrowAccountResponse response = escrowAccountService.getEscrowAccountById(id);
        return RestResponse.<EscrowAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public RestResponse<List<EscrowAccountResponse>> getEscrowAccountsByOrderId(@PathVariable Long orderId) {
        List<EscrowAccountResponse> response = escrowAccountService.getEscrowAccountsByOrderId(orderId);
        return RestResponse.<List<EscrowAccountResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/vendor/{vendorId}")
    public RestResponse<List<EscrowAccountResponse>> getEscrowAccountsByVendorId(@PathVariable Long vendorId) {
        List<EscrowAccountResponse> response = escrowAccountService.getEscrowAccountsByVendorId(vendorId);
        return RestResponse.<List<EscrowAccountResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/transaction/{transactionId}")
    public RestResponse<EscrowAccountResponse> getEscrowAccountByTransactionId(@PathVariable String transactionId) {
        EscrowAccountResponse response = escrowAccountService.getEscrowAccountByTransactionId(transactionId);
        return RestResponse.<EscrowAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PutMapping("/{id}/status")
    public RestResponse<EscrowAccountResponse> updateEscrowStatus(
            @PathVariable UUID id,
            @RequestParam EscrowStatus status) {
        EscrowAccountResponse response = escrowAccountService.updateEscrowStatus(id, status);
        return RestResponse.<EscrowAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Escrow status updated successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/release")
    public RestResponse<EscrowAccountResponse> releaseFunds(
            @PathVariable UUID id,
            @RequestParam String confirmedBy,
            @RequestParam(required = false) String releaseNote) {
        EscrowAccountResponse response = escrowAccountService.releaseFunds(id, confirmedBy, releaseNote);
        return RestResponse.<EscrowAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Funds released successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/partial-release")
    public RestResponse<EscrowAccountResponse> releasePartialFunds(
            @PathVariable UUID id,
            @RequestParam BigDecimal amount,
            @RequestParam String confirmedBy,
            @RequestParam(required = false) String releaseNote) {
        EscrowAccountResponse response = escrowAccountService.releasePartialFunds(id, amount, confirmedBy, releaseNote);
        return RestResponse.<EscrowAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Partial funds released successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/refund")
    public RestResponse<EscrowAccountResponse> refundFunds(
            @PathVariable UUID id,
            @RequestParam String confirmedBy,
            @RequestParam(required = false) String releaseNote) {
        EscrowAccountResponse response = escrowAccountService.refundFunds(id, confirmedBy, releaseNote);
        return RestResponse.<EscrowAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Funds refunded successfully")
                .data(response)
                .build();
    }

    @GetMapping("/due-for-release")
    public RestResponse<List<EscrowAccountResponse>> getEscrowAccountsDueForAutoRelease() {
        List<EscrowAccountResponse> response = escrowAccountService.getEscrowAccountsDueForAutoRelease();
        return RestResponse.<List<EscrowAccountResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }
} 
