package com.winnguyen1905.payment.core.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.winnguyen1905.payment.core.model.response.NotificationResponse;
import com.winnguyen1905.payment.core.model.request.PaymentSuccessNotification;
import com.winnguyen1905.payment.core.model.request.PaymentFailureNotification;
import com.winnguyen1905.payment.core.model.request.RefundNotification;
import com.winnguyen1905.payment.core.model.request.SettlementNotification;
import com.winnguyen1905.payment.core.model.request.FraudAlertNotification;
import com.winnguyen1905.payment.core.model.request.DisputeNotification;
import com.winnguyen1905.payment.core.model.request.SubscriptionBillingNotification;
import com.winnguyen1905.payment.core.model.request.CodCollectionNotification;
import com.winnguyen1905.payment.core.model.request.DeliveryConfirmationNotification;
import com.winnguyen1905.payment.core.model.response.RestResponse;

/**
 * Feign client for communicating with the Notification service.
 * Handles sending payment-related notifications via email, SMS, and push notifications.
 */
@FeignClient(name = "notification-service", url = "${service.notification.url}")
public interface NotificationServiceClient {
    
    /**
     * Send payment success notification to customer
     * 
     * @param notification Payment success notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/payment-success")
    RestResponse<NotificationResponse> sendPaymentSuccessNotification(@RequestBody PaymentSuccessNotification notification);
    
    /**
     * Send payment failure notification to customer
     * 
     * @param notification Payment failure notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/payment-failure")
    RestResponse<NotificationResponse> sendPaymentFailureNotification(@RequestBody PaymentFailureNotification notification);
    
    /**
     * Send refund processed notification to customer
     * 
     * @param notification Refund notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/refund-processed")
    RestResponse<NotificationResponse> sendRefundProcessedNotification(@RequestBody RefundNotification notification);
    
    /**
     * Send settlement notification to vendor
     * 
     * @param notification Settlement notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/settlement")
    RestResponse<NotificationResponse> sendSettlementNotification(@RequestBody SettlementNotification notification);
    
    /**
     * Send fraud alert notification to admin
     * 
     * @param notification Fraud alert details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/fraud-alert")
    RestResponse<NotificationResponse> sendFraudAlertNotification(@RequestBody FraudAlertNotification notification);
    
    /**
     * Send dispute notification
     * 
     * @param notification Dispute notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/dispute")
    RestResponse<NotificationResponse> sendDisputeNotification(@RequestBody DisputeNotification notification);
    
    /**
     * Send subscription billing notification
     * 
     * @param notification Subscription billing notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/subscription-billing")
    RestResponse<NotificationResponse> sendSubscriptionBillingNotification(@RequestBody SubscriptionBillingNotification notification);
    
    /**
     * Send COD collection notification
     * 
     * @param notification COD collection notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/cod-collection")
    RestResponse<NotificationResponse> sendCodCollectionNotification(@RequestBody CodCollectionNotification notification);
    
    /**
     * Send delivery confirmation notification
     * 
     * @param notification Delivery confirmation notification details
     * @return Result of the notification
     */
    @PostMapping("/api/v1/notifications/delivery-confirmation")
    RestResponse<NotificationResponse> sendDeliveryConfirmationNotification(@RequestBody DeliveryConfirmationNotification notification);
} 
