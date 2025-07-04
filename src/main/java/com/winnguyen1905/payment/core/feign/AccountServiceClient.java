package com.winnguyen1905.payment.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.winnguyen1905.payment.core.model.request.SettlementNotificationRequest;
import com.winnguyen1905.payment.core.model.response.RestResponse;
import com.winnguyen1905.payment.core.model.response.VendorAccountResponse;
import com.winnguyen1905.payment.core.model.response.VendorSettlementInfoResponse;

/**
 * Feign client for communicating with the Account service.
 * Handles vendor account information and settlement notifications.
 */
@FeignClient(name = "account-service", url = "${service.account.url}")
public interface AccountServiceClient {
    
    /**
     * Get vendor account information
     * 
     * @param vendorId Vendor ID
     * @return Vendor account details
     */
    @GetMapping("/api/v1/vendors/{vendorId}/account")
    RestResponse<VendorAccountResponse> getVendorAccountInfo(@PathVariable("vendorId") Long vendorId);
    
    /**
     * Get vendor settlement information including bank account details
     * 
     * @param vendorId Vendor ID
     * @return Vendor settlement information
     */
    @GetMapping("/api/v1/vendors/{vendorId}/settlement-info")
    RestResponse<VendorSettlementInfoResponse> getVendorSettlementInfo(@PathVariable("vendorId") Long vendorId);
    
    /**
     * Notify vendor account service about a new settlement
     * 
     * @param vendorId Vendor ID
     * @param request Settlement notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/vendors/{vendorId}/settlements/notify")
    RestResponse<Void> notifySettlement(
            @PathVariable("vendorId") Long vendorId,
            @RequestBody SettlementNotificationRequest request);
} 
