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

import com.winnguyen1905.payment.core.model.request.PaymentRequest;
import com.winnguyen1905.payment.core.model.request.PaymentSessionRequest;
import com.winnguyen1905.payment.core.model.response.PaymentResponse;
import com.winnguyen1905.payment.core.model.response.PaymentSessionResponse;
import com.winnguyen1905.payment.core.service.PaymentService;
import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments/core")
public class PaymentController {

  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/create")
  public ResponseEntity<PaymentSessionResponse> createPaymentSession(@Valid @RequestBody PaymentSessionRequest request) {
    PaymentSessionResponse response = paymentService.createPaymentSession(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping
  public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
    PaymentResponse response = paymentService.createPayment(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<PaymentResponse> getPayment(@PathVariable UUID id) {
    PaymentResponse response = paymentService.getPaymentById(id);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/number/{paymentNumber}")
  public ResponseEntity<PaymentResponse> getPaymentByNumber(@PathVariable String paymentNumber) {
    PaymentResponse response = paymentService.getPaymentByPaymentNumber(paymentNumber);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/transaction/{transactionId}")
  public ResponseEntity<PaymentResponse> getPaymentByTransactionId(@PathVariable String transactionId) {
    PaymentResponse response = paymentService.getPaymentByTransactionId(transactionId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/order/{orderId}")
  public ResponseEntity<List<PaymentResponse>> getPaymentsByOrderId(@PathVariable Long orderId) {
    List<PaymentResponse> response = paymentService.getPaymentsByOrderId(orderId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/customer/{customerId}")
  public ResponseEntity<List<PaymentResponse>> getPaymentsByCustomerId(@PathVariable Long customerId) {
    List<PaymentResponse> response = paymentService.getPaymentsByCustomerId(customerId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/status/{status}")
  public ResponseEntity<List<PaymentResponse>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
    List<PaymentResponse> response = paymentService.getPaymentsByStatus(status);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/order/{orderId}/status/{status}")
  public ResponseEntity<List<PaymentResponse>> getPaymentsByOrderIdAndStatus(
      @PathVariable Long orderId,
      @PathVariable PaymentStatus status) {
    List<PaymentResponse> response = paymentService.getPaymentsByOrderIdAndStatus(orderId, status);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/customer/{customerId}/status/{status}")
  public ResponseEntity<List<PaymentResponse>> getPaymentsByCustomerIdAndStatus(
      @PathVariable Long customerId,
      @PathVariable PaymentStatus status) {
    List<PaymentResponse> response = paymentService.getPaymentsByCustomerIdAndStatus(customerId, status);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<PaymentResponse> updatePaymentStatus(
      @PathVariable UUID id,
      @RequestParam PaymentStatus status) {
    PaymentResponse response = paymentService.updatePaymentStatus(id, status);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/authorize")
  public ResponseEntity<PaymentResponse> authorizePayment(
      @PathVariable UUID id,
      @RequestParam String authorizationCode) {
    PaymentResponse response = paymentService.authorizePayment(id, authorizationCode);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/capture")
  public ResponseEntity<PaymentResponse> capturePayment(
      @PathVariable UUID id,
      @RequestParam(required = false) BigDecimal captureAmount) {
    PaymentResponse response = paymentService.capturePayment(id, captureAmount);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/complete")
  public ResponseEntity<PaymentResponse> completePayment(@PathVariable UUID id) {
    PaymentResponse response = paymentService.completePayment(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/fail")
  public ResponseEntity<PaymentResponse> failPayment(
      @PathVariable UUID id,
      @RequestParam String failureReason) {
    PaymentResponse response = paymentService.failPayment(id, failureReason);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/cancel")
  public ResponseEntity<PaymentResponse> cancelPayment(@PathVariable UUID id) {
    PaymentResponse response = paymentService.cancelPayment(id);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/metadata")
  public ResponseEntity<PaymentResponse> updatePaymentMetadata(
      @PathVariable UUID id,
      @RequestParam String metadata) {
    PaymentResponse response = paymentService.updatePaymentMetadata(id, metadata);
    return ResponseEntity.ok(response);
  }
}
