package com.winnguyen1905.payment.core.feign.model.response;

import java.time.Instant;
import java.util.UUID;

/**
 * Response model for product stock information
 */
public class StockInfoResponse {
    private UUID productId;
    private Integer totalQuantity;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private Integer committedQuantity;
    private Integer onOrderQuantity;
    private Integer lowStockThreshold;
    private Boolean autoReorderEnabled;
    private Integer reorderPoint;
    private Integer reorderQuantity;
    private Instant lastStockUpdate;
    private String warehouseLocation;

    // Getters and setters
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
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

    public Integer getCommittedQuantity() {
        return committedQuantity;
    }

    public void setCommittedQuantity(Integer committedQuantity) {
        this.committedQuantity = committedQuantity;
    }

    public Integer getOnOrderQuantity() {
        return onOrderQuantity;
    }

    public void setOnOrderQuantity(Integer onOrderQuantity) {
        this.onOrderQuantity = onOrderQuantity;
    }

    public Integer getLowStockThreshold() {
        return lowStockThreshold;
    }

    public void setLowStockThreshold(Integer lowStockThreshold) {
        this.lowStockThreshold = lowStockThreshold;
    }

    public Boolean getAutoReorderEnabled() {
        return autoReorderEnabled;
    }

    public void setAutoReorderEnabled(Boolean autoReorderEnabled) {
        this.autoReorderEnabled = autoReorderEnabled;
    }

    public Integer getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(Integer reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public Instant getLastStockUpdate() {
        return lastStockUpdate;
    }

    public void setLastStockUpdate(Instant lastStockUpdate) {
        this.lastStockUpdate = lastStockUpdate;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }
} 
