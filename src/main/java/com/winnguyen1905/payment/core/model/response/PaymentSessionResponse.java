package com.winnguyen1905.payment.core.model.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for payment session creation.
 * Contains the payment ID and URL for client redirection.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionResponse {

  private UUID paymentId;

  private String paymentUrl;

  private String sessionId; // Gateway session ID

  private Long orderId;

  private BigDecimal amount;

  private String currency;

  private String status;

  private Instant expiresAt;

  private Instant createdAt;

  private String callbackUrl;

  private String cancelUrl;
  
  // Provider-specific response fields
  private String qrCodeUrl; // QR code image URL
  
  private String qrCodeData; // QR code data string
  
  private String deeplink; // Mobile app deeplink
  
  private String transactionId; // Provider transaction ID
  
  private String paymentCode; // Provider payment code
}
