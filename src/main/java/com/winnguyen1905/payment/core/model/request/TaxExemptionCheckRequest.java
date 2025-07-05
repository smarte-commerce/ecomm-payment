package com.winnguyen1905.payment.core.model.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import com.winnguyen1905.payment.core.feign.model.request.TaxAddress;

/**
 * Request model for checking tax exemptions
 */
public class TaxExemptionCheckRequest {
    @NotNull
    private UUID customerId;
    
    @NotNull
    private String productCategory;
    
    @NotNull
    private TaxAddress address;
    
    private List<String> exemptionCodes;

    // Getters and setters
    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public TaxAddress getAddress() {
        return address;
    }

    public void setAddress(TaxAddress address) {
        this.address = address;
    }

    public List<String> getExemptionCodes() {
        return exemptionCodes;
    }

    public void setExemptionCodes(List<String> exemptionCodes) {
        this.exemptionCodes = exemptionCodes;
    }
} 
