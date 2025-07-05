package com.winnguyen1905.payment.core.controller;

import java.time.Instant;
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

import com.winnguyen1905.payment.core.model.request.SubscriptionRequest;
import com.winnguyen1905.payment.core.model.response.SubscriptionResponse;
import com.winnguyen1905.payment.core.service.SubscriptionService;
import com.winnguyen1905.payment.persistance.entity.ESubscription.SubscriptionStatus;

import jakarta.validation.Valid;

/**
 * Controller for managing subscriptions and subscription lifecycle.
 * Handles subscription creation, billing cycles, trial periods, and status management.
 */
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> createSubscription(@Valid @RequestBody SubscriptionRequest request) {
        SubscriptionResponse response = subscriptionService.createSubscription(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> getSubscription(@PathVariable UUID id) {
        SubscriptionResponse response = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/number/{subscriptionNumber}")
    public ResponseEntity<SubscriptionResponse> getSubscriptionByNumber(@PathVariable String subscriptionNumber) {
        SubscriptionResponse response = subscriptionService.getSubscriptionByNumber(subscriptionNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provider-subscription/{providerSubscriptionId}")
    public ResponseEntity<SubscriptionResponse> getSubscriptionByProviderSubscriptionId(@PathVariable String providerSubscriptionId) {
        SubscriptionResponse response = subscriptionService.getSubscriptionByProviderSubscriptionId(providerSubscriptionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptionsByCustomerId(@PathVariable Long customerId) {
        List<SubscriptionResponse> response = subscriptionService.getSubscriptionsByCustomerId(customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptionsByStatus(@PathVariable SubscriptionStatus status) {
        List<SubscriptionResponse> response = subscriptionService.getSubscriptionsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/status/{status}")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptionsByCustomerIdAndStatus(
            @PathVariable Long customerId,
            @PathVariable SubscriptionStatus status) {
        List<SubscriptionResponse> response = subscriptionService.getSubscriptionsByCustomerIdAndStatus(customerId, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<SubscriptionResponse>> getSubscriptionsByPlanId(@PathVariable String planId) {
        List<SubscriptionResponse> response = subscriptionService.getSubscriptionsByPlanId(planId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/renewal/due")
    public ResponseEntity<List<SubscriptionResponse>> getActiveSubscriptionsToRenew(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant beforeDate) {
        List<SubscriptionResponse> response = subscriptionService.getActiveSubscriptionsToRenew(beforeDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/trial/conversion-due")
    public ResponseEntity<List<SubscriptionResponse>> getTrialingSubscriptionsToConvert(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant beforeDate) {
        List<SubscriptionResponse> response = subscriptionService.getTrialingSubscriptionsToConvert(beforeDate);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> updateSubscription(
            @PathVariable UUID id,
            @Valid @RequestBody SubscriptionRequest request) {
        SubscriptionResponse response = subscriptionService.updateSubscription(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SubscriptionResponse> updateSubscriptionStatus(
            @PathVariable UUID id,
            @RequestParam SubscriptionStatus status) {
        SubscriptionResponse response = subscriptionService.updateSubscriptionStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<SubscriptionResponse> cancelSubscription(@PathVariable UUID id) {
        SubscriptionResponse response = subscriptionService.cancelSubscription(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/reactivate")
    public ResponseEntity<SubscriptionResponse> reactivateSubscription(@PathVariable UUID id) {
        SubscriptionResponse response = subscriptionService.reactivateSubscription(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<SubscriptionResponse> renewSubscription(@PathVariable UUID id) {
        SubscriptionResponse response = subscriptionService.renewSubscription(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/convert-trial")
    public ResponseEntity<SubscriptionResponse> convertTrialToActive(@PathVariable UUID id) {
        SubscriptionResponse response = subscriptionService.convertTrialToActive(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/payment-method")
    public ResponseEntity<SubscriptionResponse> updatePaymentMethod(
            @PathVariable UUID id,
            @RequestParam UUID paymentMethodId) {
        SubscriptionResponse response = subscriptionService.updatePaymentMethod(id, paymentMethodId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/metadata")
    public ResponseEntity<SubscriptionResponse> updateSubscriptionMetadata(
            @PathVariable UUID id,
            @RequestParam String metadata) {
        SubscriptionResponse response = subscriptionService.updateSubscriptionMetadata(id, metadata);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<SubscriptionResponse> pauseSubscription(@PathVariable UUID id) {
        SubscriptionResponse response = subscriptionService.pauseSubscription(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<SubscriptionResponse> resumeSubscription(@PathVariable UUID id) {
        SubscriptionResponse response = subscriptionService.resumeSubscription(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable UUID id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
} 
