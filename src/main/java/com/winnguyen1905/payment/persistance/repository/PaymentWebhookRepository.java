package com.winnguyen1905.payment.persistance.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EPaymentWebhook;

@Repository
public interface PaymentWebhookRepository extends JpaRepository<EPaymentWebhook, UUID> {

  List<EPaymentWebhook> findByProviderId(UUID providerId);

  List<EPaymentWebhook> findByWebhookType(String webhookType);

  List<EPaymentWebhook> findByPaymentId(UUID paymentId);

  List<EPaymentWebhook> findBySubscriptionId(UUID subscriptionId);

  Optional<EPaymentWebhook> findByEventId(String eventId);

  List<EPaymentWebhook> findByEventType(String eventType);

  List<EPaymentWebhook> findByProcessed(boolean processed);

  @Query("SELECT pw FROM PaymentWebhook pw WHERE pw.processed = false AND pw.processingAttempts < :maxAttempts")
  List<EPaymentWebhook> findUnprocessedWebhooksWithinAttempts(@Param("maxAttempts") int maxAttempts);

  @Query("SELECT pw FROM PaymentWebhook pw WHERE pw.processed = false AND pw.receivedAt < :date")
  List<EPaymentWebhook> findUnprocessedWebhooksOlderThan(@Param("date") Instant date);
}
