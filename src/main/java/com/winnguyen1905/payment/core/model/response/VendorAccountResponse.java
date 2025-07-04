package com.winnguyen1905.payment.core.model.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for vendor account information from the Account service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendorAccountResponse {
    
    private Long vendorId;
    
    private String vendorName;
    
    private String contactEmail;
    
    private String contactPhone;
    
    private String taxId;
    
    private String accountStatus;
    
    private LocalDateTime accountCreatedDate;
    
    private Boolean verificationStatus;
    
    private LocalDateTime verifiedAt;
    
    private String country;
    
    private String currency;
    
    private Boolean canReceivePayments;
    
    private String accountType;
    
    // Financial compliance information
    private Boolean kycCompleted;
    
    private Boolean amlScreeningPassed;
} 
