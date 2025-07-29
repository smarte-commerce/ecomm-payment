package com.winnguyen1905.payment.core.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.payment.core.feign.PaymentGatewayServiceClient;
import com.winnguyen1905.payment.core.feign.PaymentGatewayServiceClient.CheckoutSessionRequest;
import com.winnguyen1905.payment.core.feign.PaymentGatewayServiceClient.CheckoutSessionResponse;
import com.winnguyen1905.payment.core.model.request.PaymentRequest;
import com.winnguyen1905.payment.core.model.request.PaymentSessionRequest;
import com.winnguyen1905.payment.core.model.response.PaymentResponse;
import com.winnguyen1905.payment.core.model.response.PaymentSessionResponse;
import com.winnguyen1905.payment.core.model.response.RestResponse;
import com.winnguyen1905.payment.core.service.PaymentService;
import com.winnguyen1905.payment.exception.ResourceNotFoundException;
import com.winnguyen1905.payment.persistance.entity.EPayment;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod;
import com.winnguyen1905.payment.persistance.entity.EPaymentProvider;
import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentStatus;
import com.winnguyen1905.payment.persistance.repository.PaymentMethodRepository;
import com.winnguyen1905.payment.persistance.repository.PaymentProviderRepository;
import com.winnguyen1905.payment.persistance.repository.PaymentRepository;

/**
 * Implementation of PaymentService for managing payments.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final PaymentProviderRepository paymentProviderRepository;
  private final PaymentMethodRepository paymentMethodRepository;
  private final PaymentGatewayServiceClient paymentGatewayServiceClient;

  public PaymentServiceImpl(PaymentRepository paymentRepository,
      PaymentProviderRepository paymentProviderRepository,
      PaymentMethodRepository paymentMethodRepository,
      PaymentGatewayServiceClient paymentGatewayServiceClient) {
    this.paymentRepository = paymentRepository;
    this.paymentProviderRepository = paymentProviderRepository;
    this.paymentMethodRepository = paymentMethodRepository;
    this.paymentGatewayServiceClient = paymentGatewayServiceClient;
  }

  @Override
  public PaymentSessionResponse createPaymentSession(PaymentSessionRequest request) {
    // Create checkout session request for payment gateway
    CheckoutSessionRequest checkoutRequest = new CheckoutSessionRequest();
    checkoutRequest.setAmount(request.getAmount());
    checkoutRequest.setCurrency(request.getCurrency());
    checkoutRequest.setCustomerId(request.getCustomerId().toString());
    checkoutRequest.setOrderId(request.getOrderId().toString());
    checkoutRequest.setSuccessUrl(request.getCallbackUrl());
    checkoutRequest.setCancelUrl(request.getCancelUrl());
    checkoutRequest.setDescription(request.getDescription());
    checkoutRequest.setMetadata(request.getMetadata());

    // Set expiration time if provided
    if (request.getExpiresInMinutes() != null) {
      long expiresAt = Instant.now().plusSeconds(request.getExpiresInMinutes() * 60).getEpochSecond();
      checkoutRequest.setExpiresAt(expiresAt);
    }

    // Set payment methods if provided
    if (request.getPaymentMethods() != null && !request.getPaymentMethods().isEmpty()) {
      String[] paymentMethodTypes = request.getPaymentMethods().split(",");
      checkoutRequest.setPaymentMethodTypes(paymentMethodTypes);
    }
    
    // Set provider-specific fields
    if (request.getLocale() != null) {
      checkoutRequest.setVnp_Locale(request.getLocale());
    }
    
    if (request.getOrderType() != null) {
      checkoutRequest.setVnp_OrderType(request.getOrderType());
    }
    
    if (request.getCustomerIp() != null) {
      checkoutRequest.setVnp_IpAddr(request.getCustomerIp());
    }
    
    if (request.getGenerateQr() != null && request.getGenerateQr()) {
      checkoutRequest.setQrCode("true");
    }
    
    if (request.getMobileAppScheme() != null) {
      checkoutRequest.setAppScheme(request.getMobileAppScheme());
    }

    // Call payment gateway to create checkout session
    RestResponse<CheckoutSessionResponse> gatewayResponse = paymentGatewayServiceClient
        .createCheckoutSession(checkoutRequest);

    CheckoutSessionResponse sessionResponse = gatewayResponse.data();

    // Create payment record in our database
    EPayment payment = EPayment.builder()
        .orderId(request.getOrderId())
        .customerId(request.getCustomerId())
        .paymentNumber(generatePaymentNumber()) // Helper method to generate unique payment number
        .paymentIntentId(sessionResponse.getId()) // Use session ID as payment intent ID
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .feeAmount(BigDecimal.ZERO) // Will be updated when payment is processed
        .netAmount(request.getAmount()) // Will be updated when payment is processed
        .status(PaymentStatus.PENDING)
        .paymentType(EPayment.PaymentType.PURCHASE)
        .description(request.getDescription())
        .metadata(request.getMetadata())
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    EPayment savedPayment = paymentRepository.save(payment);

    // Build response
    return PaymentSessionResponse.builder()
        .paymentId(savedPayment.getId())
        .paymentUrl(sessionResponse.getUrl())
        .sessionId(sessionResponse.getId())
        .orderId(request.getOrderId())
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .status(sessionResponse.getStatus())
        .expiresAt(
            sessionResponse.getExpiresAt() != null ? Instant.ofEpochSecond(sessionResponse.getExpiresAt()) : null)
        .createdAt(savedPayment.getCreatedAt())
        .callbackUrl(request.getCallbackUrl())
        .cancelUrl(request.getCancelUrl())
        // Provider-specific fields
        .qrCodeUrl(sessionResponse.getQrCodeUrl())
        .qrCodeData(sessionResponse.getQrCodeData())
        .deeplink(sessionResponse.getDeeplink())
        .transactionId(sessionResponse.getTransactionId())
        .paymentCode(sessionResponse.getPaymentCode())
        .build();
  }

  @Override
  public PaymentResponse createPayment(PaymentRequest request) {
    EPaymentProvider provider = null;
    if (request.getProviderId() != null) {
      provider = paymentProviderRepository.findById(request.getProviderId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Payment provider not found with ID: " + request.getProviderId()));
    }

    EPaymentMethod paymentMethod = null;
    if (request.getPaymentMethodId() != null) {
      paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Payment method not found with ID: " + request.getPaymentMethodId()));
    }

    EPayment payment = EPayment.builder()
        .orderId(request.getOrderId())
        .customerId(request.getCustomerId())
        .paymentNumber(request.getPaymentNumber())
        .provider(provider)
        .paymentMethod(paymentMethod)
        .paymentIntentId(request.getPaymentIntentId())
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .feeAmount(request.getFeeAmount())
        .netAmount(request.getNetAmount())
        .status(PaymentStatus.PENDING)
        .paymentType(request.getPaymentType())
        .description(request.getDescription())
        .metadata(request.getMetadata())
        .authorizationCode(request.getAuthorizationCode())
        .transactionId(request.getTransactionId())
        .gatewayResponse(request.getGatewayResponse())
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    EPayment savedPayment = paymentRepository.save(payment);
    return mapToResponse(savedPayment);
  }

  @Override
  @Transactional(readOnly = true)
  public PaymentResponse getPaymentById(UUID id) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));
    return mapToResponse(payment);
  }

  @Override
  @Transactional(readOnly = true)
  public PaymentResponse getPaymentByPaymentNumber(String paymentNumber) {
    EPayment payment = paymentRepository.findByPaymentNumber(paymentNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with number: " + paymentNumber));
    return mapToResponse(payment);
  }

  @Override
  @Transactional(readOnly = true)
  public PaymentResponse getPaymentByTransactionId(String transactionId) {
    EPayment payment = paymentRepository.findByTransactionId(transactionId)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with transaction ID: " + transactionId));
    return mapToResponse(payment);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PaymentResponse> getPaymentsByOrderId(Long orderId) {
    List<EPayment> payments = paymentRepository.findByOrderId(orderId);
    return payments.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<PaymentResponse> getPaymentsByCustomerId(Long customerId) {
    List<EPayment> payments = paymentRepository.findByCustomerId(customerId);
    return payments.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<PaymentResponse> getPaymentsByStatus(PaymentStatus status) {
    List<EPayment> payments = paymentRepository.findByStatus(status);
    return payments.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<PaymentResponse> getPaymentsByOrderIdAndStatus(Long orderId, PaymentStatus status) {
    List<EPayment> payments = paymentRepository.findByOrderIdAndStatus(orderId, status);
    return payments.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<PaymentResponse> getPaymentsByCustomerIdAndStatus(Long customerId, PaymentStatus status) {
    List<EPayment> payments = paymentRepository.findByCustomerIdAndStatus(customerId, status);
    return payments.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public PaymentResponse updatePaymentStatus(UUID id, PaymentStatus status) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

    payment.setStatus(status);
    payment.setUpdatedAt(Instant.now());

    if (status == PaymentStatus.COMPLETED) {
      payment.setCompletedAt(Instant.now());
    }

    EPayment updatedPayment = paymentRepository.save(payment);
    return mapToResponse(updatedPayment);
  }

  @Override
  public PaymentResponse authorizePayment(UUID id, String authorizationCode) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

    payment.setStatus(PaymentStatus.AUTHORIZED);
    payment.setAuthorizationCode(authorizationCode);
    payment.setAuthorizedAt(Instant.now());
    payment.setUpdatedAt(Instant.now());

    EPayment updatedPayment = paymentRepository.save(payment);
    return mapToResponse(updatedPayment);
  }

  @Override
  public PaymentResponse capturePayment(UUID id, BigDecimal captureAmount) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

    if (payment.getStatus() != PaymentStatus.AUTHORIZED) {
      throw new IllegalStateException("Payment must be authorized before capture");
    }

    payment.setStatus(PaymentStatus.CAPTURED);
    payment.setCapturedAt(Instant.now());
    payment.setUpdatedAt(Instant.now());

    if (captureAmount != null && captureAmount.compareTo(payment.getAmount()) < 0) {
      payment.setAmount(captureAmount);
      // Recalculate net amount if needed
      payment.setNetAmount(captureAmount.subtract(payment.getFeeAmount()));
    }

    EPayment updatedPayment = paymentRepository.save(payment);
    return mapToResponse(updatedPayment);
  }

  @Override
  public PaymentResponse completePayment(UUID id) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

    payment.setStatus(PaymentStatus.COMPLETED);
    payment.setCompletedAt(Instant.now());
    payment.setUpdatedAt(Instant.now());

    EPayment updatedPayment = paymentRepository.save(payment);
    return mapToResponse(updatedPayment);
  }

  @Override
  public PaymentResponse failPayment(UUID id, String failureReason) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

    payment.setStatus(PaymentStatus.FAILED);
    payment.setFailureReason(failureReason);
    payment.setUpdatedAt(Instant.now());

    EPayment updatedPayment = paymentRepository.save(payment);
    return mapToResponse(updatedPayment);
  }

  @Override
  public PaymentResponse cancelPayment(UUID id) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

    payment.setStatus(PaymentStatus.CANCELLED);
    payment.setUpdatedAt(Instant.now());

    EPayment updatedPayment = paymentRepository.save(payment);
    return mapToResponse(updatedPayment);
  }

  @Override
  public PaymentResponse updatePaymentMetadata(UUID id, String metadata) {
    EPayment payment = paymentRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Payment not found with ID: " + id));

    payment.setMetadata(metadata);
    payment.setUpdatedAt(Instant.now());

    EPayment updatedPayment = paymentRepository.save(payment);
    return mapToResponse(updatedPayment);
  }

  private PaymentResponse mapToResponse(EPayment payment) {
    return PaymentResponse.builder()
        .id(payment.getId())
        .orderId(payment.getOrderId())
        .customerId(payment.getCustomerId())
        .paymentNumber(payment.getPaymentNumber())
        .providerId(payment.getProvider() != null ? payment.getProvider().getId() : null)
        .providerName(payment.getProvider() != null ? payment.getProvider().getProviderName() : null)
        .paymentMethodId(payment.getPaymentMethod() != null ? payment.getPaymentMethod().getId() : null)
        .paymentMethodDisplayName(
            payment.getPaymentMethod() != null ? payment.getPaymentMethod().getDisplayName() : null)
        .paymentIntentId(payment.getPaymentIntentId())
        .amount(payment.getAmount())
        .currency(payment.getCurrency())
        .feeAmount(payment.getFeeAmount())
        .netAmount(payment.getNetAmount())
        .status(payment.getStatus())
        .paymentType(payment.getPaymentType())
        .description(payment.getDescription())
        .metadata(payment.getMetadata())
        .authorizationCode(payment.getAuthorizationCode())
        .transactionId(payment.getTransactionId())
        .gatewayResponse(payment.getGatewayResponse())
        .failureReason(payment.getFailureReason())
        .authorizedAt(payment.getAuthorizedAt())
        .capturedAt(payment.getCapturedAt())
        .completedAt(payment.getCompletedAt())
        .expiresAt(payment.getExpiresAt())
        .createdAt(payment.getCreatedAt())
        .updatedAt(payment.getUpdatedAt())
        .build();
  }

  /**
   * Generate a unique payment number
   * Format: PAY-{timestamp}-{random}
   */
  private String generatePaymentNumber() {
    long timestamp = System.currentTimeMillis();
    int random = (int) (Math.random() * 1000);
    return String.format("PAY-%d-%03d", timestamp, random);
  }
}
