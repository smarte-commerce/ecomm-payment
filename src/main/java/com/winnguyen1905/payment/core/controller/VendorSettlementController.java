package com.winnguyen1905.payment.core.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winnguyen1905.payment.core.model.request.VendorSettlementRequest;
import com.winnguyen1905.payment.core.model.response.RestResponse;
import com.winnguyen1905.payment.core.model.response.VendorAccountResponse;
import com.winnguyen1905.payment.core.model.response.VendorSettlementInfoResponse;
import com.winnguyen1905.payment.core.model.response.VendorSettlementResponse;
import com.winnguyen1905.payment.core.service.VendorSettlementService;
import com.winnguyen1905.payment.persistance.entity.VendorSettlement.SettlementStatus;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/settlements")
public class VendorSettlementController {

    private final VendorSettlementService vendorSettlementService;

    @Autowired
    public VendorSettlementController(VendorSettlementService vendorSettlementService) {
        this.vendorSettlementService = vendorSettlementService;
    }

    @PostMapping
    public RestResponse<VendorSettlementResponse> createSettlement(@Valid @RequestBody VendorSettlementRequest request) {
        VendorSettlementResponse response = vendorSettlementService.createSettlement(request);
        return RestResponse.<VendorSettlementResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Vendor settlement created successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public RestResponse<VendorSettlementResponse> getSettlement(@PathVariable UUID id) {
        VendorSettlementResponse response = vendorSettlementService.getSettlementById(id);
        return RestResponse.<VendorSettlementResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/vendor/{vendorId}")
    public RestResponse<List<VendorSettlementResponse>> getSettlementsByVendorId(@PathVariable Long vendorId) {
        List<VendorSettlementResponse> response = vendorSettlementService.getSettlementsByVendorId(vendorId);
        return RestResponse.<List<VendorSettlementResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/order/{orderId}")
    public RestResponse<List<VendorSettlementResponse>> getSettlementsByOrderId(@PathVariable Long orderId) {
        List<VendorSettlementResponse> response = vendorSettlementService.getSettlementsByOrderId(orderId);
        return RestResponse.<List<VendorSettlementResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PutMapping("/{id}/status")
    public RestResponse<VendorSettlementResponse> updateSettlementStatus(
            @PathVariable UUID id,
            @RequestParam SettlementStatus status) {
        VendorSettlementResponse response = vendorSettlementService.updateSettlementStatus(id, status);
        return RestResponse.<VendorSettlementResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Settlement status updated successfully")
                .data(response)
                .build();
    }

    @GetMapping("/calculate-commission")
    public RestResponse<BigDecimal> calculateCommission(
            @RequestParam Long vendorId,
            @RequestParam Long orderId,
            @RequestParam BigDecimal orderAmount) {
        BigDecimal commission = vendorSettlementService.calculateCommission(vendorId, orderId, orderAmount);
        return RestResponse.<BigDecimal>builder()
                .statusCode(HttpStatus.OK.value())
                .data(commission)
                .build();
    }

    @PostMapping("/{id}/process")
    public RestResponse<VendorSettlementResponse> processSettlementPayment(@PathVariable UUID id) {
        VendorSettlementResponse response = vendorSettlementService.processSettlementPayment(id);
        return RestResponse.<VendorSettlementResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Settlement payment processed successfully")
                .data(response)
                .build();
    }

    @GetMapping("/due-for-payment")
    public RestResponse<List<VendorSettlementResponse>> getSettlementsDueForPayment(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dueDate) {
        List<VendorSettlementResponse> response = vendorSettlementService.getSettlementsDueForPayment(dueDate);
        return RestResponse.<List<VendorSettlementResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @PostMapping("/{id}/notify")
    public RestResponse<Boolean> notifyVendorAccountService(@PathVariable UUID id) {
        boolean success = vendorSettlementService.notifyVendorAccountService(id);
        return RestResponse.<Boolean>builder()
                .statusCode(HttpStatus.OK.value())
                .message(success ? "Vendor notified successfully" : "Failed to notify vendor")
                .data(success)
                .build();
    }

    @GetMapping("/vendor/{vendorId}/account-info")
    public RestResponse<VendorAccountResponse> getVendorAccountInfo(@PathVariable Long vendorId) {
        VendorAccountResponse response = vendorSettlementService.getVendorAccountInfo(vendorId);
        return RestResponse.<VendorAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @GetMapping("/vendor/{vendorId}/settlement-info")
    public RestResponse<VendorSettlementInfoResponse> getVendorSettlementInfo(@PathVariable Long vendorId) {
        VendorSettlementInfoResponse response = vendorSettlementService.getVendorSettlementInfo(vendorId);
        return RestResponse.<VendorSettlementInfoResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .data(response)
                .build();
    }
} 
