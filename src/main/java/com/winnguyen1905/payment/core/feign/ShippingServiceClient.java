package com.winnguyen1905.payment.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.winnguyen1905.payment.core.model.response.RestResponse;

/**
 * Feign client for communicating with the Shipping service.
 * Handles delivery tracking information and shipping status updates for payment processing.
 */
@FeignClient(name = "shipping-service", url = "${service.shipping.url}")
public interface ShippingServiceClient {
    
    /**
     * Get shipping information by order ID
     * 
     * @param orderId Order ID
     * @return Shipping information
     */
    @GetMapping("/api/v1/shipments/order/{orderId}")
    RestResponse<ShippingInfo> getShippingInfoByOrderId(@PathVariable("orderId") Long orderId);
    
    /**
     * Get delivery status by tracking number
     * 
     * @param trackingNumber Tracking number
     * @return Delivery status
     */
    @GetMapping("/api/v1/shipments/tracking/{trackingNumber}")
    RestResponse<DeliveryStatus> getDeliveryStatus(@PathVariable("trackingNumber") String trackingNumber);
    
    /**
     * Get delivery confirmation details
     * 
     * @param orderId Order ID
     * @return Delivery confirmation details
     */
    @GetMapping("/api/v1/shipments/order/{orderId}/delivery-confirmation")
    RestResponse<DeliveryConfirmation> getDeliveryConfirmation(@PathVariable("orderId") Long orderId);
    
    /**
     * Update payment confirmation for COD orders
     * 
     * @param orderId Order ID
     * @param codPaymentUpdate COD payment update details
     * @return Result of the update
     */
    @PutMapping("/api/v1/shipments/order/{orderId}/cod-payment")
    RestResponse<Void> updateCodPaymentStatus(
            @PathVariable("orderId") Long orderId,
            @RequestBody CodPaymentUpdate codPaymentUpdate);
    
    /**
     * Notify about payment completion for shipped orders
     * 
     * @param orderId Order ID
     * @param paymentCompletionNotification Payment completion notification
     * @return Result of the notification
     */
    @PutMapping("/api/v1/shipments/order/{orderId}/payment-completed")
    RestResponse<Void> notifyPaymentCompleted(
            @PathVariable("orderId") Long orderId,
            @RequestBody PaymentCompletionNotification paymentCompletionNotification);
    
    /**
     * Get shipping provider information
     * 
     * @param providerId Shipping provider ID
     * @return Shipping provider details
     */
    @GetMapping("/api/v1/shipping-providers/{providerId}")
    RestResponse<ShippingProvider> getShippingProvider(@PathVariable("providerId") Long providerId);
    
    /**
     * Get estimated delivery date
     * 
     * @param orderId Order ID
     * @return Estimated delivery date
     */
    @GetMapping("/api/v1/shipments/order/{orderId}/estimated-delivery")
    RestResponse<EstimatedDelivery> getEstimatedDeliveryDate(@PathVariable("orderId") Long orderId);
    
    /**
     * Update delivery instructions for payment-related changes
     * 
     * @param orderId Order ID
     * @param instructions Delivery instructions
     * @return Result of the update
     */
    @PutMapping("/api/v1/shipments/order/{orderId}/delivery-instructions")
    RestResponse<Void> updateDeliveryInstructions(
            @PathVariable("orderId") Long orderId,
            @RequestParam("instructions") String instructions);
    
    /**
     * Data Transfer Objects for Shipping Service Communication
     */
    
    /**
     * Shipping information DTO
     */
    public static class ShippingInfo {
        private Long orderId;
        private String trackingNumber;
        private Long shippingProviderId;
        private String shippingProviderName;
        private String shippingMethod;
        private String status;
        private java.time.LocalDateTime shippedDate;
        private java.time.LocalDateTime estimatedDeliveryDate;
        private java.time.LocalDateTime actualDeliveryDate;
        private String shippingAddress;
        private java.math.BigDecimal shippingCost;
        private String currency;
        
        // Getters and setters
        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        
        public String getTrackingNumber() { return trackingNumber; }
        public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
        
        public Long getShippingProviderId() { return shippingProviderId; }
        public void setShippingProviderId(Long shippingProviderId) { this.shippingProviderId = shippingProviderId; }
        
        public String getShippingProviderName() { return shippingProviderName; }
        public void setShippingProviderName(String shippingProviderName) { this.shippingProviderName = shippingProviderName; }
        
        public String getShippingMethod() { return shippingMethod; }
        public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public java.time.LocalDateTime getShippedDate() { return shippedDate; }
        public void setShippedDate(java.time.LocalDateTime shippedDate) { this.shippedDate = shippedDate; }
        
        public java.time.LocalDateTime getEstimatedDeliveryDate() { return estimatedDeliveryDate; }
        public void setEstimatedDeliveryDate(java.time.LocalDateTime estimatedDeliveryDate) { this.estimatedDeliveryDate = estimatedDeliveryDate; }
        
        public java.time.LocalDateTime getActualDeliveryDate() { return actualDeliveryDate; }
        public void setActualDeliveryDate(java.time.LocalDateTime actualDeliveryDate) { this.actualDeliveryDate = actualDeliveryDate; }
        
        public String getShippingAddress() { return shippingAddress; }
        public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
        
        public java.math.BigDecimal getShippingCost() { return shippingCost; }
        public void setShippingCost(java.math.BigDecimal shippingCost) { this.shippingCost = shippingCost; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
    }
    
    /**
     * Delivery status DTO
     */
    public static class DeliveryStatus {
        private String trackingNumber;
        private String status;
        private String statusDescription;
        private java.time.LocalDateTime lastUpdated;
        private String currentLocation;
        private java.time.LocalDateTime deliveredDate;
        private String recipientName;
        private String deliveryNotes;
        
        // Getters and setters
        public String getTrackingNumber() { return trackingNumber; }
        public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getStatusDescription() { return statusDescription; }
        public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }
        
        public java.time.LocalDateTime getLastUpdated() { return lastUpdated; }
        public void setLastUpdated(java.time.LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
        
        public String getCurrentLocation() { return currentLocation; }
        public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
        
        public java.time.LocalDateTime getDeliveredDate() { return deliveredDate; }
        public void setDeliveredDate(java.time.LocalDateTime deliveredDate) { this.deliveredDate = deliveredDate; }
        
        public String getRecipientName() { return recipientName; }
        public void setRecipientName(String recipientName) { this.recipientName = recipientName; }
        
        public String getDeliveryNotes() { return deliveryNotes; }
        public void setDeliveryNotes(String deliveryNotes) { this.deliveryNotes = deliveryNotes; }
    }
    
    /**
     * Delivery confirmation DTO
     */
    public static class DeliveryConfirmation {
        private Long orderId;
        private String trackingNumber;
        private Boolean isDelivered;
        private java.time.LocalDateTime deliveredDate;
        private String confirmationMethod;
        private String customerConfirmation;
        private String proofOfDelivery;
        private Integer confirmationWindowDays;
        private java.time.LocalDateTime autoConfirmDate;
        
        // Getters and setters
        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        
        public String getTrackingNumber() { return trackingNumber; }
        public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
        
        public Boolean getIsDelivered() { return isDelivered; }
        public void setIsDelivered(Boolean isDelivered) { this.isDelivered = isDelivered; }
        
        public java.time.LocalDateTime getDeliveredDate() { return deliveredDate; }
        public void setDeliveredDate(java.time.LocalDateTime deliveredDate) { this.deliveredDate = deliveredDate; }
        
        public String getConfirmationMethod() { return confirmationMethod; }
        public void setConfirmationMethod(String confirmationMethod) { this.confirmationMethod = confirmationMethod; }
        
        public String getCustomerConfirmation() { return customerConfirmation; }
        public void setCustomerConfirmation(String customerConfirmation) { this.customerConfirmation = customerConfirmation; }
        
        public String getProofOfDelivery() { return proofOfDelivery; }
        public void setProofOfDelivery(String proofOfDelivery) { this.proofOfDelivery = proofOfDelivery; }
        
        public Integer getConfirmationWindowDays() { return confirmationWindowDays; }
        public void setConfirmationWindowDays(Integer confirmationWindowDays) { this.confirmationWindowDays = confirmationWindowDays; }
        
        public java.time.LocalDateTime getAutoConfirmDate() { return autoConfirmDate; }
        public void setAutoConfirmDate(java.time.LocalDateTime autoConfirmDate) { this.autoConfirmDate = autoConfirmDate; }
    }
    
    /**
     * COD payment update DTO
     */
    public static class CodPaymentUpdate {
        private String transactionId;
        private String paymentStatus;
        private java.math.BigDecimal collectedAmount;
        private String currency;
        private java.time.LocalDateTime collectionDate;
        private String deliveryPersonnelId;
        private String collectionNotes;
        
        // Getters and setters
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        
        public String getPaymentStatus() { return paymentStatus; }
        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
        
        public java.math.BigDecimal getCollectedAmount() { return collectedAmount; }
        public void setCollectedAmount(java.math.BigDecimal collectedAmount) { this.collectedAmount = collectedAmount; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public java.time.LocalDateTime getCollectionDate() { return collectionDate; }
        public void setCollectionDate(java.time.LocalDateTime collectionDate) { this.collectionDate = collectionDate; }
        
        public String getDeliveryPersonnelId() { return deliveryPersonnelId; }
        public void setDeliveryPersonnelId(String deliveryPersonnelId) { this.deliveryPersonnelId = deliveryPersonnelId; }
        
        public String getCollectionNotes() { return collectionNotes; }
        public void setCollectionNotes(String collectionNotes) { this.collectionNotes = collectionNotes; }
    }
    
    /**
     * Payment completion notification DTO
     */
    public static class PaymentCompletionNotification {
        private String paymentId;
        private String transactionId;
        private java.math.BigDecimal amount;
        private String currency;
        private String paymentMethod;
        private java.time.LocalDateTime completedAt;
        private String notes;
        
        // Getters and setters
        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
        
        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        
        public java.math.BigDecimal getAmount() { return amount; }
        public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        
        public java.time.LocalDateTime getCompletedAt() { return completedAt; }
        public void setCompletedAt(java.time.LocalDateTime completedAt) { this.completedAt = completedAt; }
        
        public String getNotes() { return notes; }
        public void setNotes(String notes) { this.notes = notes; }
    }
    
    /**
     * Shipping provider DTO
     */
    public static class ShippingProvider {
        private Long id;
        private String name;
        private String code;
        private String trackingUrlTemplate;
        private String apiEndpoint;
        private Boolean isActive;
        private java.util.List<String> supportedServices;
        
        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        
        public String getTrackingUrlTemplate() { return trackingUrlTemplate; }
        public void setTrackingUrlTemplate(String trackingUrlTemplate) { this.trackingUrlTemplate = trackingUrlTemplate; }
        
        public String getApiEndpoint() { return apiEndpoint; }
        public void setApiEndpoint(String apiEndpoint) { this.apiEndpoint = apiEndpoint; }
        
        public Boolean getIsActive() { return isActive; }
        public void setIsActive(Boolean isActive) { this.isActive = isActive; }
        
        public java.util.List<String> getSupportedServices() { return supportedServices; }
        public void setSupportedServices(java.util.List<String> supportedServices) { this.supportedServices = supportedServices; }
    }
    
    /**
     * Estimated delivery DTO
     */
    public static class EstimatedDelivery {
        private Long orderId;
        private java.time.LocalDateTime estimatedDate;
        private java.time.LocalDateTime earliestDate;
        private java.time.LocalDateTime latestDate;
        private String deliveryWindow;
        private String confidence;
        
        // Getters and setters
        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        
        public java.time.LocalDateTime getEstimatedDate() { return estimatedDate; }
        public void setEstimatedDate(java.time.LocalDateTime estimatedDate) { this.estimatedDate = estimatedDate; }
        
        public java.time.LocalDateTime getEarliestDate() { return earliestDate; }
        public void setEarliestDate(java.time.LocalDateTime earliestDate) { this.earliestDate = earliestDate; }
        
        public java.time.LocalDateTime getLatestDate() { return latestDate; }
        public void setLatestDate(java.time.LocalDateTime latestDate) { this.latestDate = latestDate; }
        
        public String getDeliveryWindow() { return deliveryWindow; }
        public void setDeliveryWindow(String deliveryWindow) { this.deliveryWindow = deliveryWindow; }
        
        public String getConfidence() { return confidence; }
        public void setConfidence(String confidence) { this.confidence = confidence; }
    }
} 
