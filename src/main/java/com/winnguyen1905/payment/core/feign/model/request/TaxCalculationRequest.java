package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Request model for tax calculation
 */
public class TaxCalculationRequest {
    @NotNull
    private BigDecimal amount;
    
    @NotNull
    @Size(min = 2, max = 3)
    private String currency;
    
    @NotNull
    private TaxAddress billingAddress;
    
    private TaxAddress shippingAddress;
    
    @NotNull
    private String productCategory;
    
    private UUID customerId;
    private List<String> exemptionCodes;
    private String taxType; // SALES, VAT, GST, etc.
    private Map<String, Object> metadata;

    // Getters and setters
    public BigDecimal getAmount() { 
        return amount; 
    }
    
    public void setAmount(BigDecimal amount) { 
        this.amount = amount; 
    }
    
    public String getCurrency() { 
        return currency; 
    }
    
    public void setCurrency(String currency) { 
        this.currency = currency; 
    }
    
    public TaxAddress getBillingAddress() { 
        return billingAddress; 
    }
    
    public void setBillingAddress(TaxAddress billingAddress) { 
        this.billingAddress = billingAddress; 
    }
    
    public TaxAddress getShippingAddress() { 
        return shippingAddress; 
    }
    
    public void setShippingAddress(TaxAddress shippingAddress) { 
        this.shippingAddress = shippingAddress; 
    }
    
    public String getProductCategory() { 
        return productCategory; 
    }
    
    public void setProductCategory(String productCategory) { 
        this.productCategory = productCategory; 
    }
    
    public UUID getCustomerId() { 
        return customerId; 
    }
    
    public void setCustomerId(UUID customerId) { 
        this.customerId = customerId; 
    }
    
    public List<String> getExemptionCodes() { 
        return exemptionCodes; 
    }
    
    public void setExemptionCodes(List<String> exemptionCodes) { 
        this.exemptionCodes = exemptionCodes; 
    }
    
    public String getTaxType() { 
        return taxType; 
    }
    
    public void setTaxType(String taxType) { 
        this.taxType = taxType; 
    }
    
    public Map<String, Object> getMetadata() { 
        return metadata; 
    }
    
    public void setMetadata(Map<String, Object> metadata) { 
        this.metadata = metadata; 
    }
} 
