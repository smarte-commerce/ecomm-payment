package com.winnguyen1905.payment.core.feign.model.response;

import java.time.Instant;
import java.util.UUID;

/**
 * Response model for stock update operations
 */
public class StockUpdateResponse {
    private UUID productId;
    private Integer previousQuantity;
    private Integer newQuantity;
    private Integer changeQuantity;
    private String reason;
    private Instant updatedAt;
    private String reference;
    private Boolean success;
    private String message;

    // Getters and setters
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public Integer getPreviousQuantity() {
        return previousQuantity;
    }

    public void setPreviousQuantity(Integer previousQuantity) {
        this.previousQuantity = previousQuantity;
    }

    public Integer getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(Integer newQuantity) {
        this.newQuantity = newQuantity;
    }

    public Integer getChangeQuantity() {
        return changeQuantity;
    }

    public void setChangeQuantity(Integer changeQuantity) {
        this.changeQuantity = changeQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 
