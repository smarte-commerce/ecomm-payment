package com.winnguyen1905.payment.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.winnguyen1905.payment.core.model.response.RestResponse;

/**
 * Feign client for communicating with the Order service.
 * Handles order information retrieval and payment status updates.
 */
@FeignClient(name = "order-service", url = "${service.order.url}")
public interface OrderServiceClient {

  /**
   * Get order details by order ID
   * 
   * @param orderId Order ID
   * @return Order details
   */
  @GetMapping("/api/v1/orders/{orderId}")
  RestResponse<OrderInfo> getOrderById(@PathVariable("orderId") Long orderId);

  /**
   * Get order status by order ID
   * 
   * @param orderId Order ID
   * @return Order status
   */
  @GetMapping("/api/v1/orders/{orderId}/status")
  RestResponse<OrderStatus> getOrderStatus(@PathVariable("orderId") Long orderId);

  /**
   * Update order payment status
   * 
   * @param orderId       Order ID
   * @param paymentStatus Payment status
   * @return Result of the update
   */
  @PutMapping("/api/v1/orders/{orderId}/payment-status")
  RestResponse<Void> updateOrderPaymentStatus(
      @PathVariable("orderId") Long orderId,
      @RequestParam("paymentStatus") String paymentStatus);

  /**
   * Update order fulfillment status
   * 
   * @param orderId           Order ID
   * @param fulfillmentStatus Fulfillment status
   * @return Result of the update
   */
  @PutMapping("/api/v1/orders/{orderId}/fulfillment-status")
  RestResponse<Void> updateOrderFulfillmentStatus(
      @PathVariable("orderId") Long orderId,
      @RequestParam("fulfillmentStatus") String fulfillmentStatus);

  /**
   * Get order items for refund processing
   * 
   * @param orderId Order ID
   * @return List of order items
   */
  @GetMapping("/api/v1/orders/{orderId}/items")
  RestResponse<java.util.List<OrderItem>> getOrderItems(@PathVariable("orderId") Long orderId);

  /**
   * Get order delivery information
   * 
   * @param orderId Order ID
   * @return Delivery information
   */
  @GetMapping("/api/v1/orders/{orderId}/delivery")
  RestResponse<OrderDeliveryInfo> getOrderDeliveryInfo(@PathVariable("orderId") Long orderId);

  /**
   * Notify order service about payment completion
   * 
   * @param orderId             Order ID
   * @param paymentNotification Payment notification details
   * @return Result of the notification
   */
  @PutMapping("/api/v1/orders/{orderId}/payment-completed")
  RestResponse<Void> notifyPaymentCompleted(
      @PathVariable("orderId") Long orderId,
      @RequestBody PaymentCompletionNotification paymentNotification);

  /**
   * Notify order service about payment failure
   * 
   * @param orderId                    Order ID
   * @param paymentFailureNotification Payment failure notification details
   * @return Result of the notification
   */
  @PutMapping("/api/v1/orders/{orderId}/payment-failed")
  RestResponse<Void> notifyPaymentFailed(
      @PathVariable("orderId") Long orderId,
      @RequestBody PaymentFailureNotification paymentFailureNotification);

  /**
   * Notify order service about refund processing
   * 
   * @param orderId            Order ID
   * @param refundNotification Refund notification details
   * @return Result of the notification
   */
  @PutMapping("/api/v1/orders/{orderId}/refund-processed")
  RestResponse<Void> notifyRefundProcessed(
      @PathVariable("orderId") Long orderId,
      @RequestBody RefundNotification refundNotification);

  /**
   * Get order total amount for validation
   * 
   * @param orderId Order ID
   * @return Order total amount
   */
  @GetMapping("/api/v1/orders/{orderId}/total")
  RestResponse<OrderTotal> getOrderTotal(@PathVariable("orderId") Long orderId);

  /**
   * Data Transfer Objects for Order Service Communication
   */

  /**
   * Order information DTO
   */
  public static class OrderInfo {
    private Long id;
    private Long customerId;
    private Long vendorId;
    private String orderNumber;
    private String status;
    private java.math.BigDecimal totalAmount;
    private String currency;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;

    // Getters and setters
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Long getCustomerId() {
      return customerId;
    }

    public void setCustomerId(Long customerId) {
      this.customerId = customerId;
    }

    public Long getVendorId() {
      return vendorId;
    }

    public void setVendorId(Long vendorId) {
      this.vendorId = vendorId;
    }

    public String getOrderNumber() {
      return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
      this.orderNumber = orderNumber;
    }

    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public java.math.BigDecimal getTotalAmount() {
      return totalAmount;
    }

    public void setTotalAmount(java.math.BigDecimal totalAmount) {
      this.totalAmount = totalAmount;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public java.time.LocalDateTime getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
      this.createdAt = createdAt;
    }

    public java.time.LocalDateTime getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
      this.updatedAt = updatedAt;
    }
  }

  /**
   * Order status DTO
   */
  public static class OrderStatus {
    private String status;
    private String paymentStatus;
    private String fulfillmentStatus;

    // Getters and setters
    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getPaymentStatus() {
      return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
      this.paymentStatus = paymentStatus;
    }

    public String getFulfillmentStatus() {
      return fulfillmentStatus;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
      this.fulfillmentStatus = fulfillmentStatus;
    }
  }

  /**
   * Order item DTO
   */
  public static class OrderItem {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private java.math.BigDecimal unitPrice;
    private java.math.BigDecimal totalPrice;

    // Getters and setters
    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public Long getProductId() {
      return productId;
    }

    public void setProductId(Long productId) {
      this.productId = productId;
    }

    public String getProductName() {
      return productName;
    }

    public void setProductName(String productName) {
      this.productName = productName;
    }

    public Integer getQuantity() {
      return quantity;
    }

    public void setQuantity(Integer quantity) {
      this.quantity = quantity;
    }

    public java.math.BigDecimal getUnitPrice() {
      return unitPrice;
    }

    public void setUnitPrice(java.math.BigDecimal unitPrice) {
      this.unitPrice = unitPrice;
    }

    public java.math.BigDecimal getTotalPrice() {
      return totalPrice;
    }

    public void setTotalPrice(java.math.BigDecimal totalPrice) {
      this.totalPrice = totalPrice;
    }
  }

  /**
   * Order delivery information DTO
   */
  public static class OrderDeliveryInfo {
    private String shippingAddress;
    private String trackingNumber;
    private String shippingProvider;
    private java.time.LocalDateTime estimatedDeliveryDate;
    private java.time.LocalDateTime actualDeliveryDate;

    // Getters and setters
    public String getShippingAddress() {
      return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
      this.shippingAddress = shippingAddress;
    }

    public String getTrackingNumber() {
      return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
      this.trackingNumber = trackingNumber;
    }

    public String getShippingProvider() {
      return shippingProvider;
    }

    public void setShippingProvider(String shippingProvider) {
      this.shippingProvider = shippingProvider;
    }

    public java.time.LocalDateTime getEstimatedDeliveryDate() {
      return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(java.time.LocalDateTime estimatedDeliveryDate) {
      this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public java.time.LocalDateTime getActualDeliveryDate() {
      return actualDeliveryDate;
    }

    public void setActualDeliveryDate(java.time.LocalDateTime actualDeliveryDate) {
      this.actualDeliveryDate = actualDeliveryDate;
    }
  }

  /**
   * Order total DTO
   */
  public static class OrderTotal {
    private java.math.BigDecimal subtotal;
    private java.math.BigDecimal taxAmount;
    private java.math.BigDecimal shippingAmount;
    private java.math.BigDecimal discountAmount;
    private java.math.BigDecimal totalAmount;
    private String currency;

    // Getters and setters
    public java.math.BigDecimal getSubtotal() {
      return subtotal;
    }

    public void setSubtotal(java.math.BigDecimal subtotal) {
      this.subtotal = subtotal;
    }

    public java.math.BigDecimal getTaxAmount() {
      return taxAmount;
    }

    public void setTaxAmount(java.math.BigDecimal taxAmount) {
      this.taxAmount = taxAmount;
    }

    public java.math.BigDecimal getShippingAmount() {
      return shippingAmount;
    }

    public void setShippingAmount(java.math.BigDecimal shippingAmount) {
      this.shippingAmount = shippingAmount;
    }

    public java.math.BigDecimal getDiscountAmount() {
      return discountAmount;
    }

    public void setDiscountAmount(java.math.BigDecimal discountAmount) {
      this.discountAmount = discountAmount;
    }

    public java.math.BigDecimal getTotalAmount() {
      return totalAmount;
    }

    public void setTotalAmount(java.math.BigDecimal totalAmount) {
      this.totalAmount = totalAmount;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }
  }

  /**
   * Payment completion notification DTO
   */
  public static class PaymentCompletionNotification {
    private String paymentId;
    private String transactionId;
    private java.math.BigDecimal amount;
    private String currency;
    private java.time.LocalDateTime completedAt;

    // Getters and setters
    public String getPaymentId() {
      return paymentId;
    }

    public void setPaymentId(String paymentId) {
      this.paymentId = paymentId;
    }

    public String getTransactionId() {
      return transactionId;
    }

    public void setTransactionId(String transactionId) {
      this.transactionId = transactionId;
    }

    public java.math.BigDecimal getAmount() {
      return amount;
    }

    public void setAmount(java.math.BigDecimal amount) {
      this.amount = amount;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public java.time.LocalDateTime getCompletedAt() {
      return completedAt;
    }

    public void setCompletedAt(java.time.LocalDateTime completedAt) {
      this.completedAt = completedAt;
    }
  }

  /**
   * Payment failure notification DTO
   */
  public static class PaymentFailureNotification {
    private String paymentId;
    private String transactionId;
    private String failureReason;
    private java.time.LocalDateTime failedAt;

    // Getters and setters
    public String getPaymentId() {
      return paymentId;
    }

    public void setPaymentId(String paymentId) {
      this.paymentId = paymentId;
    }

    public String getTransactionId() {
      return transactionId;
    }

    public void setTransactionId(String transactionId) {
      this.transactionId = transactionId;
    }

    public String getFailureReason() {
      return failureReason;
    }

    public void setFailureReason(String failureReason) {
      this.failureReason = failureReason;
    }

    public java.time.LocalDateTime getFailedAt() {
      return failedAt;
    }

    public void setFailedAt(java.time.LocalDateTime failedAt) {
      this.failedAt = failedAt;
    }
  }

  /**
   * Refund notification DTO
   */
  public static class RefundNotification {
    private String refundId;
    private String paymentId;
    private java.math.BigDecimal refundAmount;
    private String currency;
    private String reason;
    private java.time.LocalDateTime processedAt;

    // Getters and setters
    public String getRefundId() {
      return refundId;
    }

    public void setRefundId(String refundId) {
      this.refundId = refundId;
    }

    public String getPaymentId() {
      return paymentId;
    }

    public void setPaymentId(String paymentId) {
      this.paymentId = paymentId;
    }

    public java.math.BigDecimal getRefundAmount() {
      return refundAmount;
    }

    public void setRefundAmount(java.math.BigDecimal refundAmount) {
      this.refundAmount = refundAmount;
    }

    public String getCurrency() {
      return currency;
    }

    public void setCurrency(String currency) {
      this.currency = currency;
    }

    public String getReason() {
      return reason;
    }

    public void setReason(String reason) {
      this.reason = reason;
    }

    public java.time.LocalDateTime getProcessedAt() {
      return processedAt;
    }

    public void setProcessedAt(java.time.LocalDateTime processedAt) {
      this.processedAt = processedAt;
    }
  }
}
