package com.winnguyen1905.payment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;
import java.util.Map;

/**
 * Configuration for payment providers like VNPay, Momo, etc.
 */
@Configuration
@ConfigurationProperties(prefix = "payment.providers")
@Data
public class PaymentProviderConfig {

  private VnPayConfig vnpay;
  private MomoConfig momo;

  @Data
  public static class VnPayConfig {
    private String tmnCode; // Terminal ID
    private String hashSecret; // Secret key for signature
    private String apiUrl; // VNPay API URL
    private String returnUrl; // Default return URL
    private String ipnUrl; // IPN callback URL
    private String version = "2.1.0"; // VNPay API version
    private String locale = "vn"; // Default language
    private String currCode = "VND"; // Currency code
    private String orderType = "other"; // Default order type
    private Boolean enabled = false;
  }

  @Data
  public static class MomoConfig {
    private String partnerCode; // Partner code
    private String accessKey; // Access key
    private String secretKey; // Secret key for signature
    private String endpoint; // Momo API endpoint
    private String notifyUrl; // IPN URL
    private String returnUrl; // Return URL
    private String requestType = "captureWallet"; // Default request type
    private Boolean autoCapture = true;
    private Boolean enabled = false;
  }

  /**
   * Get provider configuration by name
   */
  public Object getProviderConfig(String providerName) {
    switch (providerName.toLowerCase()) {
      case "vnpay":
        return vnpay;
      case "momo":
        return momo;
      default:
        return null;
    }
  }

  /**
   * Check if provider is enabled
   */
  public boolean isProviderEnabled(String providerName) {
    switch (providerName.toLowerCase()) {
      case "vnpay":
        return vnpay != null && vnpay.getEnabled();
      case "momo":
        return momo != null && momo.getEnabled();
      default:
        return false;
    }
  }
}
