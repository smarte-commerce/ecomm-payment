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

import com.winnguyen1905.payment.core.model.request.SubscriptionInvoiceRequest;
import com.winnguyen1905.payment.core.model.response.SubscriptionInvoiceResponse;
import com.winnguyen1905.payment.core.service.SubscriptionInvoiceService;
import com.winnguyen1905.payment.persistance.entity.ESubscriptionInvoice.InvoiceStatus;

import jakarta.validation.Valid;

/**
 * Controller for managing subscription invoices and billing cycles.
 * Handles invoice creation, payment processing, and billing management.
 */
@RestController
@RequestMapping("/api/v1/subscription-invoices")
public class SubscriptionInvoiceController {

    private final SubscriptionInvoiceService subscriptionInvoiceService;

    @Autowired
    public SubscriptionInvoiceController(SubscriptionInvoiceService subscriptionInvoiceService) {
        this.subscriptionInvoiceService = subscriptionInvoiceService;
    }

    @PostMapping
    public ResponseEntity<SubscriptionInvoiceResponse> createSubscriptionInvoice(@Valid @RequestBody SubscriptionInvoiceRequest request) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.createSubscriptionInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionInvoiceResponse> getSubscriptionInvoice(@PathVariable UUID id) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.getSubscriptionInvoiceById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/invoice-number/{invoiceNumber}")
    public ResponseEntity<SubscriptionInvoiceResponse> getSubscriptionInvoiceByInvoiceNumber(@PathVariable String invoiceNumber) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.getSubscriptionInvoiceByInvoiceNumber(invoiceNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provider-invoice/{providerInvoiceId}")
    public ResponseEntity<SubscriptionInvoiceResponse> getSubscriptionInvoiceByProviderInvoiceId(@PathVariable String providerInvoiceId) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.getSubscriptionInvoiceByProviderInvoiceId(providerInvoiceId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/subscription/{subscriptionId}")
    public ResponseEntity<List<SubscriptionInvoiceResponse>> getSubscriptionInvoicesBySubscriptionId(@PathVariable UUID subscriptionId) {
        List<SubscriptionInvoiceResponse> response = subscriptionInvoiceService.getSubscriptionInvoicesBySubscriptionId(subscriptionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<List<SubscriptionInvoiceResponse>> getSubscriptionInvoicesByPaymentId(@PathVariable UUID paymentId) {
        List<SubscriptionInvoiceResponse> response = subscriptionInvoiceService.getSubscriptionInvoicesByPaymentId(paymentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SubscriptionInvoiceResponse>> getSubscriptionInvoicesByStatus(@PathVariable InvoiceStatus status) {
        List<SubscriptionInvoiceResponse> response = subscriptionInvoiceService.getSubscriptionInvoicesByStatus(status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<SubscriptionInvoiceResponse>> getOverdueSubscriptionInvoices(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant asOfDate) {
        if (asOfDate == null) {
            asOfDate = Instant.now();
        }
        List<SubscriptionInvoiceResponse> response = subscriptionInvoiceService.getOverdueSubscriptionInvoices(asOfDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/due-before")
    public ResponseEntity<List<SubscriptionInvoiceResponse>> getSubscriptionInvoicesByStatusAndDueDateBefore(
            @RequestParam InvoiceStatus status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant beforeDate) {
        List<SubscriptionInvoiceResponse> response = subscriptionInvoiceService.getSubscriptionInvoicesByStatusAndDueDateBefore(status, beforeDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/period")
    public ResponseEntity<List<SubscriptionInvoiceResponse>> getSubscriptionInvoicesWithinPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant periodStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant periodEnd) {
        List<SubscriptionInvoiceResponse> response = subscriptionInvoiceService.getSubscriptionInvoicesWithinPeriod(periodStart, periodEnd);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/high-attempts")
    public ResponseEntity<List<SubscriptionInvoiceResponse>> getSubscriptionInvoicesWithHighAttemptCount(
            @RequestParam Integer minAttempts) {
        List<SubscriptionInvoiceResponse> response = subscriptionInvoiceService.getSubscriptionInvoicesWithHighAttemptCount(minAttempts);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionInvoiceResponse> updateSubscriptionInvoice(
            @PathVariable UUID id,
            @Valid @RequestBody SubscriptionInvoiceRequest request) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.updateSubscriptionInvoice(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<SubscriptionInvoiceResponse> updateSubscriptionInvoiceStatus(
            @PathVariable UUID id,
            @RequestParam InvoiceStatus status) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.updateSubscriptionInvoiceStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/mark-paid")
    public ResponseEntity<SubscriptionInvoiceResponse> markAsPaid(
            @PathVariable UUID id,
            @RequestParam UUID paymentId) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.markAsPaid(id, paymentId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/mark-uncollectible")
    public ResponseEntity<SubscriptionInvoiceResponse> markAsUncollectible(@PathVariable UUID id) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.markAsUncollectible(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/void")
    public ResponseEntity<SubscriptionInvoiceResponse> voidInvoice(@PathVariable UUID id) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.voidInvoice(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/finalize")
    public ResponseEntity<SubscriptionInvoiceResponse> finalizeInvoice(@PathVariable UUID id) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.finalizeInvoice(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/next-payment-attempt")
    public ResponseEntity<SubscriptionInvoiceResponse> updateNextPaymentAttempt(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant nextAttempt) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.updateNextPaymentAttempt(id, nextAttempt);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/increment-attempts")
    public ResponseEntity<SubscriptionInvoiceResponse> incrementAttemptCount(@PathVariable UUID id) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.incrementAttemptCount(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/metadata")
    public ResponseEntity<SubscriptionInvoiceResponse> updateSubscriptionInvoiceMetadata(
            @PathVariable UUID id,
            @RequestParam String metadata) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.updateSubscriptionInvoiceMetadata(id, metadata);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscription/{subscriptionId}/generate-next")
    public ResponseEntity<SubscriptionInvoiceResponse> generateNextInvoice(@PathVariable UUID subscriptionId) {
        SubscriptionInvoiceResponse response = subscriptionInvoiceService.generateNextInvoice(subscriptionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/process-payment-retries")
    public ResponseEntity<Integer> processPaymentRetries(@RequestParam Integer maxAttempts) {
        int processedCount = subscriptionInvoiceService.processPaymentRetries(maxAttempts);
        return ResponseEntity.ok(processedCount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriptionInvoice(@PathVariable UUID id) {
        subscriptionInvoiceService.deleteSubscriptionInvoice(id);
        return ResponseEntity.noContent().build();
    }
} 
