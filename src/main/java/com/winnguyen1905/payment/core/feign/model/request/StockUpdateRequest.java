package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Request model for updating product stock
 */
public class StockUpdateRequest {
    @NotNull
    private UUID productId;

    @NotNull
    private Integer quantity;

    @NotNull
    private String reason;

    private String notes;
    private UUID orderId;
    private String reference;

    // Getters and setters
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
} 
