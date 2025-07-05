package com.winnguyen1905.payment.core.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.winnguyen1905.payment.core.model.request.PaymentMethodRequest;
import com.winnguyen1905.payment.core.model.response.PaymentMethodResponse;
import com.winnguyen1905.payment.core.service.PaymentMethodService;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.MethodType;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.VerificationStatus;

import jakarta.validation.Valid;

/**
 * Controller for managing customer payment methods.
 * Handles creation, validation, activation/deactivation of payment methods.
 */
@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping
    public ResponseEntity<PaymentMethodResponse> createPaymentMethod(@Valid @RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse response = paymentMethodService.createPaymentMethod(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> getPaymentMethod(@PathVariable UUID id) {
        PaymentMethodResponse response = paymentMethodService.getPaymentMethodById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentMethodResponse>> getPaymentMethodsByCustomerId(@PathVariable Long customerId) {
        List<PaymentMethodResponse> response = paymentMethodService.getPaymentMethodsByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/active")
    public ResponseEntity<List<PaymentMethodResponse>> getActivePaymentMethodsByCustomerId(@PathVariable Long customerId) {
        List<PaymentMethodResponse> response = paymentMethodService.getActivePaymentMethodsByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/default")
    public ResponseEntity<PaymentMethodResponse> getDefaultPaymentMethodByCustomerId(@PathVariable Long customerId) {
        PaymentMethodResponse response = paymentMethodService.getDefaultPaymentMethodByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/type/{methodType}")
    public ResponseEntity<List<PaymentMethodResponse>> getActivePaymentMethodsByCustomerAndType(
            @PathVariable Long customerId,
            @PathVariable MethodType methodType) {
        List<PaymentMethodResponse> response = paymentMethodService.getActivePaymentMethodsByCustomerAndType(customerId, methodType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provider-method/{providerPaymentMethodId}")
    public ResponseEntity<PaymentMethodResponse> getPaymentMethodByProviderMethodId(@PathVariable String providerPaymentMethodId) {
        PaymentMethodResponse response = paymentMethodService.getPaymentMethodByProviderPaymentMethodId(providerPaymentMethodId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentMethodResponse> updatePaymentMethod(
            @PathVariable UUID id,
            @Valid @RequestBody PaymentMethodRequest request) {
        PaymentMethodResponse response = paymentMethodService.updatePaymentMethod(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<PaymentMethodResponse> deactivatePaymentMethod(@PathVariable UUID id) {
        PaymentMethodResponse response = paymentMethodService.deactivatePaymentMethod(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/set-default")
    public ResponseEntity<PaymentMethodResponse> setAsDefaultPaymentMethod(@PathVariable UUID id) {
        PaymentMethodResponse response = paymentMethodService.setAsDefault(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/verification-status")
    public ResponseEntity<PaymentMethodResponse> updateVerificationStatus(
            @PathVariable UUID id,
            @RequestParam VerificationStatus verificationStatus) {
        PaymentMethodResponse response = paymentMethodService.updateVerificationStatus(id, verificationStatus);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(@PathVariable UUID id) {
        paymentMethodService.deletePaymentMethod(id);
        return ResponseEntity.ok().build();
    }
} 
