package com.winnguyen1905.payment.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for vendor settlement information from the Account service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorSettlementInfoResponse {
    
    private Long vendorId;
    
    private String vendorName;
    
    // Banking information
    private String bankName;
    
    private String bankAccountNumber;
    
    private String bankAccountName;
    
    private String bankRoutingNumber;
    
    private String swiftCode;
    
    private String ibanNumber;
    
    private String bankAccountCurrency;
    
    private String bankCountry;
    
    private String bankBranchCode;
    
    // Settlement preference
    private String preferredSettlementCurrency;
    
    private String preferredPaymentMethod;
    
    private String paymentFrequency;
    
    private Integer paymentThreshold;
    
    private Boolean autoSettlement;
    
    // For digital payment methods (e.g., PayPal)
    private String digitalPaymentEmail;
    
    private String digitalPaymentId;
    
    private String digitalPaymentProvider;
    
    // Verification status
    private Boolean bankAccountVerified;
} 
