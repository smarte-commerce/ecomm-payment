package com.winnguyen1905.payment.core.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.winnguyen1905.payment.core.model.request.SubscriptionInvoiceRequest;
import com.winnguyen1905.payment.core.model.response.SubscriptionInvoiceResponse;
import com.winnguyen1905.payment.persistance.entity.ESubscriptionInvoice.InvoiceStatus;

/**
 * Service interface for managing subscription invoices.
 */
public interface SubscriptionInvoiceService {
    
    /**
     * Create a new subscription invoice
     * 
     * @param request Subscription invoice details
     * @return Created subscription invoice
     */
    SubscriptionInvoiceResponse createSubscriptionInvoice(SubscriptionInvoiceRequest request);
    
    /**
     * Get a subscription invoice by ID
     * 
     * @param id Subscription invoice ID
     * @return Subscription invoice details
     */
    SubscriptionInvoiceResponse getSubscriptionInvoiceById(UUID id);
    
    /**
     * Get subscription invoice by invoice number
     * 
     * @param invoiceNumber Invoice number
     * @return Subscription invoice details
     */
    SubscriptionInvoiceResponse getSubscriptionInvoiceByInvoiceNumber(String invoiceNumber);
    
    /**
     * Get subscription invoice by provider invoice ID
     * 
     * @param providerInvoiceId Provider invoice ID
     * @return Subscription invoice details
     */
    SubscriptionInvoiceResponse getSubscriptionInvoiceByProviderInvoiceId(String providerInvoiceId);
    
    /**
     * Get all subscription invoices for a subscription
     * 
     * @param subscriptionId Subscription ID
     * @return List of subscription invoices
     */
    List<SubscriptionInvoiceResponse> getSubscriptionInvoicesBySubscriptionId(UUID subscriptionId);
    
    /**
     * Get subscription invoices by payment ID
     * 
     * @param paymentId Payment ID
     * @return List of subscription invoices
     */
    List<SubscriptionInvoiceResponse> getSubscriptionInvoicesByPaymentId(UUID paymentId);
    
    /**
     * Get subscription invoices by status
     * 
     * @param status Invoice status
     * @return List of subscription invoices
     */
    List<SubscriptionInvoiceResponse> getSubscriptionInvoicesByStatus(InvoiceStatus status);
    
    /**
     * Get overdue subscription invoices
     * 
     * @param asOfDate Date to check for overdue invoices
     * @return List of overdue subscription invoices
     */
    List<SubscriptionInvoiceResponse> getOverdueSubscriptionInvoices(Instant asOfDate);
    
    /**
     * Get subscription invoices due before a specific date
     * 
     * @param status Invoice status
     * @param beforeDate Due date cutoff
     * @return List of subscription invoices due before the date
     */
    List<SubscriptionInvoiceResponse> getSubscriptionInvoicesByStatusAndDueDateBefore(InvoiceStatus status, Instant beforeDate);
    
    /**
     * Get subscription invoices within a period range
     * 
     * @param periodStart Start of period range
     * @param periodEnd End of period range
     * @return List of subscription invoices within the period
     */
    List<SubscriptionInvoiceResponse> getSubscriptionInvoicesWithinPeriod(Instant periodStart, Instant periodEnd);
    
    /**
     * Get subscription invoices with high attempt counts
     * 
     * @param minAttempts Minimum attempt count
     * @return List of subscription invoices with high retry counts
     */
    List<SubscriptionInvoiceResponse> getSubscriptionInvoicesWithHighAttemptCount(Integer minAttempts);
    
    /**
     * Update subscription invoice
     * 
     * @param id Subscription invoice ID
     * @param request Updated subscription invoice details
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse updateSubscriptionInvoice(UUID id, SubscriptionInvoiceRequest request);
    
    /**
     * Update subscription invoice status
     * 
     * @param id Subscription invoice ID
     * @param status New status
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse updateSubscriptionInvoiceStatus(UUID id, InvoiceStatus status);
    
    /**
     * Mark subscription invoice as paid
     * 
     * @param id Subscription invoice ID
     * @param paymentId Payment ID
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse markAsPaid(UUID id, UUID paymentId);
    
    /**
     * Mark subscription invoice as uncollectible
     * 
     * @param id Subscription invoice ID
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse markAsUncollectible(UUID id);
    
    /**
     * Void subscription invoice
     * 
     * @param id Subscription invoice ID
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse voidInvoice(UUID id);
    
    /**
     * Finalize draft invoice (make it open)
     * 
     * @param id Subscription invoice ID
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse finalizeInvoice(UUID id);
    
    /**
     * Update next payment attempt
     * 
     * @param id Subscription invoice ID
     * @param nextAttempt Next payment attempt date
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse updateNextPaymentAttempt(UUID id, Instant nextAttempt);
    
    /**
     * Increment attempt count
     * 
     * @param id Subscription invoice ID
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse incrementAttemptCount(UUID id);
    
    /**
     * Update subscription invoice metadata
     * 
     * @param id Subscription invoice ID
     * @param metadata Metadata as JSON string
     * @return Updated subscription invoice
     */
    SubscriptionInvoiceResponse updateSubscriptionInvoiceMetadata(UUID id, String metadata);
    
    /**
     * Generate next invoice for subscription
     * 
     * @param subscriptionId Subscription ID
     * @return Generated subscription invoice
     */
    SubscriptionInvoiceResponse generateNextInvoice(UUID subscriptionId);
    
    /**
     * Process payment retry for failed invoices
     * 
     * @param maxAttempts Maximum retry attempts
     * @return Number of invoices processed for retry
     */
    int processPaymentRetries(Integer maxAttempts);
    
    /**
     * Delete subscription invoice
     * 
     * @param id Subscription invoice ID
     */
    void deleteSubscriptionInvoice(UUID id);
} 
