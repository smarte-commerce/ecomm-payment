package com.winnguyen1905.payment.core.model.request;

import java.math.BigDecimal;
import java.util.UUID;

import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for creating or updating payments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
  @NotNull(message = "Order ID cannot be null")
  private Long orderId;

  @NotNull(message = "Customer ID cannot be null")
  private Long customerId;

  @NotBlank(message = "Payment number cannot be blank")
  private String paymentNumber;

  private UUID providerId;

  private UUID paymentMethodId;

  private String paymentIntentId;

  @NotNull(message = "Amount cannot be null")
  @Positive(message = "Amount must be positive")
  private BigDecimal amount;

  @NotBlank(message = "Currency cannot be blank")
  @Size(min = 3, max = 3, message = "Currency must be 3 characters")
  private String currency;

  @NotNull(message = "Fee amount cannot be null")
  private BigDecimal feeAmount;

  @NotNull(message = "Net amount cannot be null")
  private BigDecimal netAmount;

  @NotNull(message = "Payment type cannot be null")
  private PaymentType paymentType;

  private String description;

  private String metadata;

  private String authorizationCode;

  private String transactionId;

  private String gatewayResponse;
}
