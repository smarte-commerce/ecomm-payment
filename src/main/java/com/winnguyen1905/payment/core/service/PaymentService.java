package com.winnguyen1905.payment.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.PaymentRequest;
import com.winnguyen1905.payment.core.model.request.PaymentSessionRequest;
import com.winnguyen1905.payment.core.model.response.PaymentResponse;
import com.winnguyen1905.payment.core.model.response.PaymentSessionResponse;
import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentStatus;

/**
 * Service interface for managing payments.
 */
public interface PaymentService {

  /**
   * Create a payment session with external gateway
   * Returns a payment URL for client redirection
   * 
   * @param request Payment session details
   * @return Payment session with payment URL
   */
  PaymentSessionResponse createPaymentSession(PaymentSessionRequest request);

  /**
   * Create a new payment
   * 
   * @param request Payment details
   * @return Created payment
   */
  PaymentResponse createPayment(PaymentRequest request);

  /**
   * Get a payment by ID
   * 
   * @param id Payment ID
   * @return Payment details
   */
  PaymentResponse getPaymentById(UUID id);

  /**
   * Get payment by payment number
   * 
   * @param paymentNumber Payment number
   * @return Payment details
   */
  PaymentResponse getPaymentByPaymentNumber(String paymentNumber);

  /**
   * Get payment by transaction ID
   * 
   * @param transactionId Transaction ID
   * @return Payment details
   */
  PaymentResponse getPaymentByTransactionId(String transactionId);

  /**
   * Get all payments for an order
   * 
   * @param orderId Order ID
   * @return List of payments
   */
  List<PaymentResponse> getPaymentsByOrderId(Long orderId);

  /**
   * Get all payments for a customer
   * 
   * @param customerId Customer ID
   * @return List of payments
   */
  List<PaymentResponse> getPaymentsByCustomerId(Long customerId);

  /**
   * Get payments by status
   * 
   * @param status Payment status
   * @return List of payments
   */
  List<PaymentResponse> getPaymentsByStatus(PaymentStatus status);

  /**
   * Get payments by order ID and status
   * 
   * @param orderId Order ID
   * @param status  Payment status
   * @return List of payments
   */
  List<PaymentResponse> getPaymentsByOrderIdAndStatus(Long orderId, PaymentStatus status);

  /**
   * Get payments by customer ID and status
   * 
   * @param customerId Customer ID
   * @param status     Payment status
   * @return List of payments
   */
  List<PaymentResponse> getPaymentsByCustomerIdAndStatus(Long customerId, PaymentStatus status);

  /**
   * Update payment status
   * 
   * @param id     Payment ID
   * @param status New status
   * @return Updated payment
   */
  PaymentResponse updatePaymentStatus(UUID id, PaymentStatus status);

  /**
   * Authorize payment
   * 
   * @param id                Payment ID
   * @param authorizationCode Authorization code
   * @return Updated payment
   */
  PaymentResponse authorizePayment(UUID id, String authorizationCode);

  /**
   * Capture authorized payment
   * 
   * @param id            Payment ID
   * @param captureAmount Amount to capture (null for full amount)
   * @return Updated payment
   */
  PaymentResponse capturePayment(UUID id, BigDecimal captureAmount);

  /**
   * Complete payment
   * 
   * @param id Payment ID
   * @return Updated payment
   */
  PaymentResponse completePayment(UUID id);

  /**
   * Fail payment with reason
   * 
   * @param id            Payment ID
   * @param failureReason Reason for failure
   * @return Updated payment
   */
  PaymentResponse failPayment(UUID id, String failureReason);

  /**
   * Cancel payment
   * 
   * @param id Payment ID
   * @return Updated payment
   */
  PaymentResponse cancelPayment(UUID id);

  /**
   * Update payment metadata
   * 
   * @param id       Payment ID
   * @param metadata Metadata as JSON string
   * @return Updated payment
   */
  PaymentResponse updatePaymentMetadata(UUID id, String metadata);
}
