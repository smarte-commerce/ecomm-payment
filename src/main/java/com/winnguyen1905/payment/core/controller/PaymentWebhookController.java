package com.winnguyen1905.payment.core.controller;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winnguyen1905.payment.core.model.request.PaymentWebhookRequest;
import com.winnguyen1905.payment.core.model.response.PaymentWebhookResponse;
import com.winnguyen1905.payment.core.service.PaymentWebhookService;

import jakarta.validation.Valid;

/**
 * Controller for managing payment webhooks and webhook processing.
 * Handles webhook reception, processing, verification, and management.
 */
@RestController
@RequestMapping("/api/v1/payment-webhooks")
public class PaymentWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    public PaymentWebhookController(PaymentWebhookService paymentWebhookService) {
        this.paymentWebhookService = paymentWebhookService;
    }

    @PostMapping
    public ResponseEntity<PaymentWebhookResponse> createPaymentWebhook(@Valid @RequestBody PaymentWebhookRequest request) {
        PaymentWebhookResponse response = paymentWebhookService.createPaymentWebhook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentWebhookResponse> getPaymentWebhook(@PathVariable UUID id) {
        PaymentWebhookResponse response = paymentWebhookService.getPaymentWebhookById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PaymentWebhookResponse>> getAllPaymentWebhooks() {
        List<PaymentWebhookResponse> response = paymentWebhookService.getAllPaymentWebhooks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksByProviderId(@PathVariable UUID providerId) {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksByProviderId(providerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksByPaymentId(@PathVariable UUID paymentId) {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksByPaymentId(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/subscription/{subscriptionId}")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksBySubscriptionId(@PathVariable UUID subscriptionId) {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksBySubscriptionId(subscriptionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/webhook-type/{webhookType}")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksByWebhookType(@PathVariable String webhookType) {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksByWebhookType(webhookType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/event-type/{eventType}")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksByEventType(@PathVariable String eventType) {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksByEventType(eventType);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<PaymentWebhookResponse> getPaymentWebhookByEventId(@PathVariable String eventId) {
        PaymentWebhookResponse response = paymentWebhookService.getPaymentWebhookByEventId(eventId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/processed")
    public ResponseEntity<List<PaymentWebhookResponse>> getProcessedPaymentWebhooks() {
        List<PaymentWebhookResponse> response = paymentWebhookService.getProcessedPaymentWebhooks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unprocessed")
    public ResponseEntity<List<PaymentWebhookResponse>> getUnprocessedPaymentWebhooks() {
        List<PaymentWebhookResponse> response = paymentWebhookService.getUnprocessedPaymentWebhooks();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/errors")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksWithErrors() {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksWithErrors();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/received-between")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksReceivedBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant endDate) {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksReceivedBetween(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/high-retry")
    public ResponseEntity<List<PaymentWebhookResponse>> getPaymentWebhooksWithHighRetryCount(
            @RequestParam Integer minAttempts) {
        List<PaymentWebhookResponse> response = paymentWebhookService.getPaymentWebhooksWithHighRetryCount(minAttempts);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentWebhookResponse> updatePaymentWebhook(
            @PathVariable UUID id,
            @Valid @RequestBody PaymentWebhookRequest request) {
        PaymentWebhookResponse response = paymentWebhookService.updatePaymentWebhook(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<PaymentWebhookResponse> processPaymentWebhook(@PathVariable UUID id) {
        PaymentWebhookResponse response = paymentWebhookService.processPaymentWebhook(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/mark-processed")
    public ResponseEntity<PaymentWebhookResponse> markAsProcessed(@PathVariable UUID id) {
        PaymentWebhookResponse response = paymentWebhookService.markAsProcessed(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/mark-failed")
    public ResponseEntity<PaymentWebhookResponse> markAsFailed(
            @PathVariable UUID id,
            @RequestParam String errorMessage) {
        PaymentWebhookResponse response = paymentWebhookService.markAsFailed(id, errorMessage);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/retry")
    public ResponseEntity<PaymentWebhookResponse> retryProcessing(@PathVariable UUID id) {
        PaymentWebhookResponse response = paymentWebhookService.retryProcessing(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/webhook-data")
    public ResponseEntity<PaymentWebhookResponse> updateWebhookData(
            @PathVariable UUID id,
            @RequestParam String webhookData) {
        PaymentWebhookResponse response = paymentWebhookService.updateWebhookData(id, webhookData);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-signature")
    public ResponseEntity<Boolean> verifyWebhookSignature(
            @RequestParam UUID providerId,
            @RequestBody String payload,
            @RequestHeader("X-Webhook-Signature") String signature) {
        boolean isValid = paymentWebhookService.verifyWebhookSignature(providerId, payload, signature);
        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/process-payload")
    public ResponseEntity<PaymentWebhookResponse> processWebhookPayload(
            @RequestParam UUID providerId,
            @RequestBody String payload) {
        PaymentWebhookResponse response = paymentWebhookService.processWebhookPayload(providerId, payload);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auto-retry")
    public ResponseEntity<Integer> autoRetryFailedWebhooks(@RequestParam Integer maxAttempts) {
        int retriedCount = paymentWebhookService.autoRetryFailedWebhooks(maxAttempts);
        return ResponseEntity.ok(retriedCount);
    }

    @PostMapping("/cleanup")
    public ResponseEntity<Integer> cleanupOldWebhooks(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant olderThan) {
        int deletedCount = paymentWebhookService.cleanupOldWebhooks(olderThan);
        return ResponseEntity.ok(deletedCount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentWebhook(@PathVariable UUID id) {
        paymentWebhookService.deletePaymentWebhook(id);
        return ResponseEntity.noContent().build();
    }
} 
