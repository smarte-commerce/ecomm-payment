package com.winnguyen1905.payment.core.feign;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.winnguyen1905.payment.core.model.response.RestResponse;

/**
 * Feign client for communicating with external payment gateway services.
 * Handles payment processing, refunds, and payment method management with providers like Stripe, PayPal, Square, etc.
 */
@FeignClient(name = "payment-gateway-service", url = "${service.payment-gateway.url}")
public interface PaymentGatewayServiceClient {
    
    /**
     * Create a checkout session for hosted payment
     * 
     * @param request Checkout session creation request
     * @return Checkout session details with payment URL
     */
    @PostMapping("/api/v1/checkout-sessions")
    RestResponse<CheckoutSessionResponse> createCheckoutSession(@RequestBody CheckoutSessionRequest request);
    
    /**
     * Retrieve checkout session by ID
     * 
     * @param sessionId Checkout session ID
     * @return Checkout session details
     */
    @GetMapping("/api/v1/checkout-sessions/{sessionId}")
    RestResponse<CheckoutSessionResponse> getCheckoutSession(@PathVariable("sessionId") String sessionId);
    
    /**
     * Create a payment intent with the payment provider
     * 
     * @param request Payment intent creation request
     * @return Payment intent details
     */
    @PostMapping("/api/v1/payment-intents")
    RestResponse<PaymentIntentResponse> createPaymentIntent(@RequestBody PaymentIntentRequest request);
    
    /**
     * Retrieve payment intent by ID
     * 
     * @param paymentIntentId Payment intent ID
     * @return Payment intent details
     */
    @GetMapping("/api/v1/payment-intents/{paymentIntentId}")
    RestResponse<PaymentIntentResponse> getPaymentIntent(@PathVariable("paymentIntentId") String paymentIntentId);
    
    /**
     * Confirm payment intent
     * 
     * @param paymentIntentId Payment intent ID
     * @param request Payment confirmation details
     * @return Updated payment intent
     */
    @PostMapping("/api/v1/payment-intents/{paymentIntentId}/confirm")
    RestResponse<PaymentIntentResponse> confirmPaymentIntent(
            @PathVariable("paymentIntentId") String paymentIntentId,
            @RequestBody PaymentConfirmationRequest request);
    
    /**
     * Cancel payment intent
     * 
     * @param paymentIntentId Payment intent ID
     * @return Updated payment intent
     */
    @PostMapping("/api/v1/payment-intents/{paymentIntentId}/cancel")
    RestResponse<PaymentIntentResponse> cancelPaymentIntent(@PathVariable("paymentIntentId") String paymentIntentId);
    
    /**
     * Capture authorized payment
     * 
     * @param paymentIntentId Payment intent ID
     * @param captureAmount Amount to capture (optional)
     * @return Updated payment intent
     */
    @PostMapping("/api/v1/payment-intents/{paymentIntentId}/capture")
    RestResponse<PaymentIntentResponse> capturePayment(
            @PathVariable("paymentIntentId") String paymentIntentId,
            @RequestParam(required = false) BigDecimal captureAmount);
    
    /**
     * Create a refund for a payment
     * 
     * @param request Refund creation request
     * @return Refund details
     */
    @PostMapping("/api/v1/refunds")
    RestResponse<RefundResponse> createRefund(@RequestBody RefundCreationRequest request);
    
    /**
     * Retrieve refund by ID
     * 
     * @param refundId Refund ID
     * @return Refund details
     */
    @GetMapping("/api/v1/refunds/{refundId}")
    RestResponse<RefundResponse> getRefund(@PathVariable("refundId") String refundId);
    
    /**
     * Cancel refund
     * 
     * @param refundId Refund ID
     * @return Updated refund details
     */
    @PostMapping("/api/v1/refunds/{refundId}/cancel")
    RestResponse<RefundResponse> cancelRefund(@PathVariable("refundId") String refundId);
    
    /**
     * Create a customer in the payment provider
     * 
     * @param request Customer creation request
     * @return Customer details
     */
    @PostMapping("/api/v1/customers")
    RestResponse<GatewayCustomerResponse> createCustomer(@RequestBody GatewayCustomerRequest request);
    
    /**
     * Retrieve customer by ID
     * 
     * @param customerId Customer ID
     * @return Customer details
     */
    @GetMapping("/api/v1/customers/{customerId}")
    RestResponse<GatewayCustomerResponse> getCustomer(@PathVariable("customerId") String customerId);
    
    /**
     * Update customer information
     * 
     * @param customerId Customer ID
     * @param request Customer update request
     * @return Updated customer details
     */
    @PutMapping("/api/v1/customers/{customerId}")
    RestResponse<GatewayCustomerResponse> updateCustomer(
            @PathVariable("customerId") String customerId,
            @RequestBody GatewayCustomerRequest request);
    
    /**
     * Delete customer
     * 
     * @param customerId Customer ID
     * @return Deletion confirmation
     */
    @DeleteMapping("/api/v1/customers/{customerId}")
    RestResponse<Void> deleteCustomer(@PathVariable("customerId") String customerId);
    
    /**
     * Create a payment method for a customer
     * 
     * @param request Payment method creation request
     * @return Payment method details
     */
    @PostMapping("/api/v1/payment-methods")
    RestResponse<GatewayPaymentMethodResponse> createPaymentMethod(@RequestBody GatewayPaymentMethodRequest request);
    
    /**
     * Retrieve payment method by ID
     * 
     * @param paymentMethodId Payment method ID
     * @return Payment method details
     */
    @GetMapping("/api/v1/payment-methods/{paymentMethodId}")
    RestResponse<GatewayPaymentMethodResponse> getPaymentMethod(@PathVariable("paymentMethodId") String paymentMethodId);
    
    /**
     * Attach payment method to customer
     * 
     * @param paymentMethodId Payment method ID
     * @param customerId Customer ID
     * @return Updated payment method
     */
    @PostMapping("/api/v1/payment-methods/{paymentMethodId}/attach")
    RestResponse<GatewayPaymentMethodResponse> attachPaymentMethod(
            @PathVariable("paymentMethodId") String paymentMethodId,
            @RequestParam("customerId") String customerId);
    
    /**
     * Detach payment method from customer
     * 
     * @param paymentMethodId Payment method ID
     * @return Updated payment method
     */
    @PostMapping("/api/v1/payment-methods/{paymentMethodId}/detach")
    RestResponse<GatewayPaymentMethodResponse> detachPaymentMethod(@PathVariable("paymentMethodId") String paymentMethodId);
    
    /**
     * Create a subscription
     * 
     * @param request Subscription creation request
     * @return Subscription details
     */
    @PostMapping("/api/v1/subscriptions")
    RestResponse<GatewaySubscriptionResponse> createSubscription(@RequestBody GatewaySubscriptionRequest request);
    
    /**
     * Retrieve subscription by ID
     * 
     * @param subscriptionId Subscription ID
     * @return Subscription details
     */
    @GetMapping("/api/v1/subscriptions/{subscriptionId}")
    RestResponse<GatewaySubscriptionResponse> getSubscription(@PathVariable("subscriptionId") String subscriptionId);
    
    /**
     * Update subscription
     * 
     * @param subscriptionId Subscription ID
     * @param request Subscription update request
     * @return Updated subscription details
     */
    @PutMapping("/api/v1/subscriptions/{subscriptionId}")
    RestResponse<GatewaySubscriptionResponse> updateSubscription(
            @PathVariable("subscriptionId") String subscriptionId,
            @RequestBody GatewaySubscriptionRequest request);
    
    /**
     * Cancel subscription
     * 
     * @param subscriptionId Subscription ID
     * @return Updated subscription details
     */
    @PostMapping("/api/v1/subscriptions/{subscriptionId}/cancel")
    RestResponse<GatewaySubscriptionResponse> cancelSubscription(@PathVariable("subscriptionId") String subscriptionId);
    
    /**
     * Retrieve subscription invoices
     * 
     * @param subscriptionId Subscription ID
     * @return List of invoices
     */
    @GetMapping("/api/v1/subscriptions/{subscriptionId}/invoices")
    RestResponse<GatewayInvoiceListResponse> getSubscriptionInvoices(@PathVariable("subscriptionId") String subscriptionId);
    
    /**
     * Create a webhook endpoint
     * 
     * @param request Webhook endpoint creation request
     * @return Webhook endpoint details
     */
    @PostMapping("/api/v1/webhook-endpoints")
    RestResponse<WebhookEndpointResponse> createWebhookEndpoint(@RequestBody WebhookEndpointRequest request);
    
    /**
     * Retrieve webhook endpoint by ID
     * 
     * @param endpointId Webhook endpoint ID
     * @return Webhook endpoint details
     */
    @GetMapping("/api/v1/webhook-endpoints/{endpointId}")
    RestResponse<WebhookEndpointResponse> getWebhookEndpoint(@PathVariable("endpointId") String endpointId);
    
    /**
     * Verify webhook signature
     * 
     * @param request Webhook verification request
     * @return Verification result
     */
    @PostMapping("/api/v1/webhooks/verify")
    RestResponse<WebhookVerificationResponse> verifyWebhookSignature(@RequestBody WebhookVerificationRequest request);
    
    /**
     * Get account balance from payment provider
     * 
     * @return Account balance details
     */
    @GetMapping("/api/v1/balance")
    RestResponse<BalanceResponse> getBalance();
    
    /**
     * Create a payout to bank account
     * 
     * @param request Payout creation request
     * @return Payout details
     */
    @PostMapping("/api/v1/payouts")
    RestResponse<PayoutResponse> createPayout(@RequestBody PayoutRequest request);
    
    /**
     * Retrieve payout by ID
     * 
     * @param payoutId Payout ID
     * @return Payout details
     */
    @GetMapping("/api/v1/payouts/{payoutId}")
    RestResponse<PayoutResponse> getPayout(@PathVariable("payoutId") String payoutId);
    
    /**
     * Data Transfer Objects for Payment Gateway Communication
     */
    
    /**
     * Payment intent request DTO
     */
    class PaymentIntentRequest {
        private BigDecimal amount;
        private String currency;
        private String customerId;
        private String paymentMethodId;
        private String description;
        private Boolean captureMethod;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Payment intent response DTO
     */
    class PaymentIntentResponse {
        private String id;
        private BigDecimal amount;
        private String currency;
        private String status;
        private String customerId;
        private String paymentMethodId;
        private String clientSecret;
        private String description;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Payment confirmation request DTO
     */
    class PaymentConfirmationRequest {
        private String paymentMethodId;
        private String returnUrl;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Refund creation request DTO
     */
    class RefundCreationRequest {
        private String paymentIntentId;
        private BigDecimal amount;
        private String reason;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Refund response DTO
     */
    class RefundResponse {
        private String id;
        private String paymentIntentId;
        private BigDecimal amount;
        private String currency;
        private String status;
        private String reason;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Gateway customer request DTO
     */
    class GatewayCustomerRequest {
        private String email;
        private String name;
        private String phone;
        private String description;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Gateway customer response DTO
     */
    class GatewayCustomerResponse {
        private String id;
        private String email;
        private String name;
        private String phone;
        private String description;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Gateway payment method request DTO
     */
    class GatewayPaymentMethodRequest {
        private String type;
        private String customerId;
        private String cardNumber;
        private String cardExpMonth;
        private String cardExpYear;
        private String cardCvc;
        
        // Getters and setters
    }
    
    /**
     * Gateway payment method response DTO
     */
    class GatewayPaymentMethodResponse {
        private String id;
        private String type;
        private String customerId;
        private String cardLast4;
        private String cardBrand;
        private String cardExpMonth;
        private String cardExpYear;
        
        // Getters and setters
    }
    
    /**
     * Gateway subscription request DTO
     */
    class GatewaySubscriptionRequest {
        private String customerId;
        private String priceId;
        private String paymentMethodId;
        private Integer trialPeriodDays;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Gateway subscription response DTO
     */
    class GatewaySubscriptionResponse {
        private String id;
        private String customerId;
        private String status;
        private String priceId;
        private String paymentMethodId;
        private Long currentPeriodStart;
        private Long currentPeriodEnd;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Gateway invoice list response DTO
     */
    class GatewayInvoiceListResponse {
        private String subscriptionId;
        private String invoiceId;
        private BigDecimal amount;
        private String status;
        private Long dueDate;
        
        // Getters and setters
    }
    
    /**
     * Webhook endpoint request DTO
     */
    class WebhookEndpointRequest {
        private String url;
        private String[] enabledEvents;
        private String description;
        
        // Getters and setters
    }
    
    /**
     * Webhook endpoint response DTO
     */
    class WebhookEndpointResponse {
        private String id;
        private String url;
        private String[] enabledEvents;
        private String status;
        private String secret;
        
        // Getters and setters
    }
    
    /**
     * Webhook verification request DTO
     */
    class WebhookVerificationRequest {
        private String payload;
        private String signature;
        private String secret;
        
        // Getters and setters
    }
    
    /**
     * Webhook verification response DTO
     */
    class WebhookVerificationResponse {
        private Boolean valid;
        private String eventType;
        private String eventId;
        
        // Getters and setters
    }
    
    /**
     * Balance response DTO
     */
    class BalanceResponse {
        private BigDecimal available;
        private BigDecimal pending;
        private String currency;
        
        // Getters and setters
    }
    
    /**
     * Payout request DTO
     */
    class PayoutRequest {
        private BigDecimal amount;
        private String currency;
        private String method;
        private String description;
        private String metadata;
        
        // Getters and setters
    }
    
    /**
     * Payout response DTO
     */
    class PayoutResponse {
        private String id;
        private BigDecimal amount;
        private String currency;
        private String status;
        private String method;
        private String description;
        private Long arrivalDate;
        
        // Getters and setters
    }
    
    /**
     * Checkout session request DTO
     */
    class CheckoutSessionRequest {
        private BigDecimal amount;
        private String currency;
        private String customerId;
        private String orderId;
        private String successUrl;
        private String cancelUrl;
        private String description;
        private String[] paymentMethodTypes;
        private Long expiresAt;
        private String metadata;
        
        // Getters and setters
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }
        
        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }
        
        public String getSuccessUrl() { return successUrl; }
        public void setSuccessUrl(String successUrl) { this.successUrl = successUrl; }
        
        public String getCancelUrl() { return cancelUrl; }
        public void setCancelUrl(String cancelUrl) { this.cancelUrl = cancelUrl; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public String[] getPaymentMethodTypes() { return paymentMethodTypes; }
        public void setPaymentMethodTypes(String[] paymentMethodTypes) { this.paymentMethodTypes = paymentMethodTypes; }
        
        public Long getExpiresAt() { return expiresAt; }
        public void setExpiresAt(Long expiresAt) { this.expiresAt = expiresAt; }
        
        public String getMetadata() { return metadata; }
        public void setMetadata(String metadata) { this.metadata = metadata; }
    }
    
    /**
     * Checkout session response DTO
     */
    class CheckoutSessionResponse {
        private String id;
        private String url;
        private String status;
        private BigDecimal amount;
        private String currency;
        private String customerId;
        private String orderId;
        private String successUrl;
        private String cancelUrl;
        private Long expiresAt;
        private Long createdAt;
        private String metadata;
        
        // Getters and setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }
        
        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }
        
        public String getSuccessUrl() { return successUrl; }
        public void setSuccessUrl(String successUrl) { this.successUrl = successUrl; }
        
        public String getCancelUrl() { return cancelUrl; }
        public void setCancelUrl(String cancelUrl) { this.cancelUrl = cancelUrl; }
        
        public Long getExpiresAt() { return expiresAt; }
        public void setExpiresAt(Long expiresAt) { this.expiresAt = expiresAt; }
        
        public Long getCreatedAt() { return createdAt; }
        public void setCreatedAt(Long createdAt) { this.createdAt = createdAt; }
        
        public String getMetadata() { return metadata; }
        public void setMetadata(String metadata) { this.metadata = metadata; }
    }
} 
