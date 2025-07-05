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

import com.winnguyen1905.payment.core.model.request.PaymentTransactionRequest;
import com.winnguyen1905.payment.core.model.response.PaymentTransactionResponse;
import com.winnguyen1905.payment.core.service.PaymentTransactionService;
import com.winnguyen1905.payment.persistance.entity.PaymentTransaction.PaymentStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentTransactionController {

    private final PaymentTransactionService paymentTransactionService;

    @Autowired
    public PaymentTransactionController(PaymentTransactionService paymentTransactionService) {
        this.paymentTransactionService = paymentTransactionService;
    }

    @PostMapping
    public ResponseEntity<PaymentTransactionResponse> createPaymentTransaction(@Valid @RequestBody PaymentTransactionRequest request) {
        PaymentTransactionResponse response = paymentTransactionService.createPaymentTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTransactionResponse> getPaymentTransaction(@PathVariable UUID id) {
        PaymentTransactionResponse response = paymentTransactionService.getPaymentTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentTransactionResponse> getPaymentTransactionByTransactionId(@PathVariable String transactionId) {
        PaymentTransactionResponse response = paymentTransactionService.getPaymentTransactionByTransactionId(transactionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentTransactionResponse>> getPaymentTransactionsByOrderId(@PathVariable Long orderId) {
        List<PaymentTransactionResponse> response = paymentTransactionService.getPaymentTransactionsByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentTransactionResponse>> getPaymentTransactionsByCustomerId(@PathVariable Long customerId) {
        List<PaymentTransactionResponse> response = paymentTransactionService.getPaymentTransactionsByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<PaymentTransactionResponse>> getPaymentTransactionsByVendorId(@PathVariable Long vendorId) {
        List<PaymentTransactionResponse> response = paymentTransactionService.getPaymentTransactionsByVendorId(vendorId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PaymentTransactionResponse> updatePaymentStatus(
            @PathVariable UUID id,
            @RequestParam PaymentStatus status) {
        PaymentTransactionResponse response = paymentTransactionService.updatePaymentStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<PaymentTransactionResponse> completePayment(
            @PathVariable UUID id,
            @RequestParam String gatewayReference) {
        PaymentTransactionResponse response = paymentTransactionService.completePayment(id, gatewayReference);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/fail")
    public ResponseEntity<PaymentTransactionResponse> failPayment(
            @PathVariable UUID id,
            @RequestParam String failureReason) {
        PaymentTransactionResponse response = paymentTransactionService.failPayment(id, failureReason);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/refund")
    public ResponseEntity<PaymentTransactionResponse> refundPayment(
            @PathVariable UUID id,
            @RequestParam(required = false) BigDecimal amount) {
        PaymentTransactionResponse response = paymentTransactionService.refundPayment(id, amount);
        return ResponseEntity.ok(response);
    }
} 
