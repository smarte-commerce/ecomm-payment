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

import com.winnguyen1905.payment.core.model.request.PaymentTransactionRequest;
import com.winnguyen1905.payment.core.model.response.PaymentTransactionResponse;
import com.winnguyen1905.payment.core.model.response.RestResponse;
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
    public RestResponse<PaymentTransactionResponse> createPaymentTransaction(@Valid @RequestBody PaymentTransactionRequest request) {
        PaymentTransactionResponse response = paymentTransactionService.createPaymentTransaction(request);
        return RestResponse.<PaymentTransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Payment transaction created successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public RestResponse<PaymentTransactionResponse> getPaymentTransaction(@PathVariable UUID id) {
        PaymentTransactionResponse response = paymentTransactionService.getPaymentTransactionById(id);
        return RestResponse.<PaymentTransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/transaction/{transactionId}")
    public RestResponse<PaymentTransactionResponse> getPaymentTransactionByTransactionId(@PathVariable String transactionId) {
        PaymentTransactionResponse response = paymentTransactionService.getPaymentTransactionByTransactionId(transactionId);
        return RestResponse.<PaymentTransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public RestResponse<List<PaymentTransactionResponse>> getPaymentTransactionsByOrderId(@PathVariable Long orderId) {
        List<PaymentTransactionResponse> response = paymentTransactionService.getPaymentTransactionsByOrderId(orderId);
        return RestResponse.<List<PaymentTransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/customer/{customerId}")
    public RestResponse<List<PaymentTransactionResponse>> getPaymentTransactionsByCustomerId(@PathVariable Long customerId) {
        List<PaymentTransactionResponse> response = paymentTransactionService.getPaymentTransactionsByCustomerId(customerId);
        return RestResponse.<List<PaymentTransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/vendor/{vendorId}")
    public RestResponse<List<PaymentTransactionResponse>> getPaymentTransactionsByVendorId(@PathVariable Long vendorId) {
        List<PaymentTransactionResponse> response = paymentTransactionService.getPaymentTransactionsByVendorId(vendorId);
        return RestResponse.<List<PaymentTransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PutMapping("/{id}/status")
    public RestResponse<PaymentTransactionResponse> updatePaymentStatus(
            @PathVariable UUID id,
            @RequestParam PaymentStatus status) {
        PaymentTransactionResponse response = paymentTransactionService.updatePaymentStatus(id, status);
        return RestResponse.<PaymentTransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment status updated successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/complete")
    public RestResponse<PaymentTransactionResponse> completePayment(
            @PathVariable UUID id,
            @RequestParam String gatewayReference) {
        PaymentTransactionResponse response = paymentTransactionService.completePayment(id, gatewayReference);
        return RestResponse.<PaymentTransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment completed successfully")
                .data(response)
                .build();
    }

    @PutMapping("/{id}/fail")
    public RestResponse<PaymentTransactionResponse> failPayment(
            @PathVariable UUID id,
            @RequestParam String failureReason) {
        PaymentTransactionResponse response = paymentTransactionService.failPayment(id, failureReason);
        return RestResponse.<PaymentTransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment marked as failed")
                .data(response)
                .build();
    }

    @PostMapping("/{id}/refund")
    public RestResponse<PaymentTransactionResponse> refundPayment(
            @PathVariable UUID id,
            @RequestParam(required = false) BigDecimal amount) {
        PaymentTransactionResponse response = paymentTransactionService.refundPayment(id, amount);
        return RestResponse.<PaymentTransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Payment refund processed successfully")
                .data(response)
                .build();
    }
} 
