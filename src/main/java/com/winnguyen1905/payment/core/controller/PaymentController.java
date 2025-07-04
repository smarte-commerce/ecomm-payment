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

import com.winnguyen1905.payment.core.model.request.PaymentRequest;
import com.winnguyen1905.payment.core.model.response.PaymentResponse;
import com.winnguyen1905.payment.core.model.response.RestResponse;
import com.winnguyen1905.payment.core.service.PaymentService;
import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments/core")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public RestResponse<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Payment created successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public RestResponse<PaymentResponse> getPayment(@PathVariable UUID id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/number/{paymentNumber}")
    public RestResponse<PaymentResponse> getPaymentByNumber(@PathVariable String paymentNumber) {
        PaymentResponse response = paymentService.getPaymentByPaymentNumber(paymentNumber);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/transaction/{transactionId}")
    public RestResponse<PaymentResponse> getPaymentByTransactionId(@PathVariable String transactionId) {
        PaymentResponse response = paymentService.getPaymentByTransactionId(transactionId);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public RestResponse<List<PaymentResponse>> getPaymentsByOrderId(@PathVariable Long orderId) {
        List<PaymentResponse> response = paymentService.getPaymentsByOrderId(orderId);
        return RestResponse.<List<PaymentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public RestResponse<List<PaymentResponse>> getPaymentsByCustomerId(@PathVariable Long customerId) {
        List<PaymentResponse> response = paymentService.getPaymentsByCustomerId(customerId);
        return RestResponse.<List<PaymentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/status/{status}")
    public RestResponse<List<PaymentResponse>> getPaymentsByStatus(@PathVariable PaymentStatus status) {
        List<PaymentResponse> response = paymentService.getPaymentsByStatus(status);
        return RestResponse.<List<PaymentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/order/{orderId}/status/{status}")
    public RestResponse<List<PaymentResponse>> getPaymentsByOrderIdAndStatus(
            @PathVariable Long orderId,
            @PathVariable PaymentStatus status) {
        List<PaymentResponse> response = paymentService.getPaymentsByOrderIdAndStatus(orderId, status);
        return RestResponse.<List<PaymentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/customer/{customerId}/status/{status}")
    public RestResponse<List<PaymentResponse>> getPaymentsByCustomerIdAndStatus(
            @PathVariable Long customerId,
            @PathVariable PaymentStatus status) {
        List<PaymentResponse> response = paymentService.getPaymentsByCustomerIdAndStatus(customerId, status);
        return RestResponse.<List<PaymentResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PutMapping("/{id}/status")
    public RestResponse<PaymentResponse> updatePaymentStatus(
            @PathVariable UUID id,
            @RequestParam PaymentStatus status) {
        PaymentResponse response = paymentService.updatePaymentStatus(id, status);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment status updated successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/authorize")
    public RestResponse<PaymentResponse> authorizePayment(
            @PathVariable UUID id,
            @RequestParam String authorizationCode) {
        PaymentResponse response = paymentService.authorizePayment(id, authorizationCode);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment authorized successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/capture")
    public RestResponse<PaymentResponse> capturePayment(
            @PathVariable UUID id,
            @RequestParam(required = false) BigDecimal captureAmount) {
        PaymentResponse response = paymentService.capturePayment(id, captureAmount);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment captured successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/complete")
    public RestResponse<PaymentResponse> completePayment(@PathVariable UUID id) {
        PaymentResponse response = paymentService.completePayment(id);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment completed successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/fail")
    public RestResponse<PaymentResponse> failPayment(
            @PathVariable UUID id,
            @RequestParam String failureReason) {
        PaymentResponse response = paymentService.failPayment(id, failureReason);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment marked as failed")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/cancel")
    public RestResponse<PaymentResponse> cancelPayment(@PathVariable UUID id) {
        PaymentResponse response = paymentService.cancelPayment(id);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment cancelled successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/metadata")
    public RestResponse<PaymentResponse> updatePaymentMetadata(
            @PathVariable UUID id,
            @RequestParam String metadata) {
        PaymentResponse response = paymentService.updatePaymentMetadata(id, metadata);
        return RestResponse.<PaymentResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment metadata updated successfully")
                .data(response)
                .build();
    }
} 
