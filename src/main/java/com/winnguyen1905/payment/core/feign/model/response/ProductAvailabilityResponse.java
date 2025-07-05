package com.winnguyen1905.payment.core.feign.model.response;

import java.time.Instant;
import java.util.UUID;

/**
 * Response model for product availability
 */
public class ProductAvailabilityResponse {
    private UUID productId;
    private String productName;
    private Boolean available;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Boolean lowStock;
    private Integer lowStockThreshold;
    private String availabilityStatus; // IN_STOCK, LOW_STOCK, OUT_OF_STOCK, DISCONTINUED
    private Instant lastUpdated;

    // Getters and setters
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(Integer reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    public Boolean getLowStock() {
        return lowStock;
    }

    public void setLowStock(Boolean lowStock) {
        this.lowStock = lowStock;
    }

    public Integer getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(Integer lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
} 
