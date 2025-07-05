package com.winnguyen1905.payment.core.feign.model.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Request model for reserving product stock
 */
public class StockReservationRequest {
    @NotNull
    private UUID productId;

    @NotNull
    private UUID orderId;

    @NotNull
    private Integer quantity;

    private UUID customerId;
    private Integer reservationDurationMinutes = 30; // Default 30 minutes
    private String notes;

    // Getters and setters
    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Integer getReservationDurationMinutes() {
        return reservationDurationMinutes;
    }

    public void setReservationDurationMinutes(Integer reservationDurationMinutes) {
        this.reservationDurationMinutes = reservationDurationMinutes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
} 
