package com.winnguyen1905.payment.persistance.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.ESubscriptionInvoice;
import com.winnguyen1905.payment.persistance.entity.ESubscriptionInvoice.InvoiceStatus;

@Repository
public interface SubscriptionInvoiceRepository extends JpaRepository<ESubscriptionInvoice, UUID> {
    
    Optional<ESubscriptionInvoice> findByInvoiceNumber(String invoiceNumber);
    
    List<ESubscriptionInvoice> findBySubscriptionId(UUID subscriptionId);
    
    List<ESubscriptionInvoice> findByStatus(InvoiceStatus status);
    
    Optional<ESubscriptionInvoice> findByProviderInvoiceId(String providerInvoiceId);
    
    @Query("SELECT si FROM SubscriptionInvoice si WHERE si.status = 'OPEN' AND si.dueDate < :date")
    List<ESubscriptionInvoice> findOverdueInvoices(@Param("date") Instant date);
    
    List<ESubscriptionInvoice> findByStatusAndDueDateBefore(InvoiceStatus status, Instant dueDate);
    
    List<ESubscriptionInvoice> findByPaymentId(UUID paymentId);
} 
