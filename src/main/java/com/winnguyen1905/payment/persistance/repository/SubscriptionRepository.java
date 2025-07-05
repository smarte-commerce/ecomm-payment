package com.winnguyen1905.payment.persistance.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.ESubscription;
import com.winnguyen1905.payment.persistance.entity.ESubscription.SubscriptionStatus;

@Repository
public interface SubscriptionRepository extends JpaRepository<ESubscription, UUID> {

  Optional<ESubscription> findBySubscriptionNumber(String subscriptionNumber);

  List<ESubscription> findByCustomerId(Long customerId);

  List<ESubscription> findByStatus(SubscriptionStatus status);

  List<ESubscription> findByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);

  Optional<ESubscription> findByProviderSubscriptionId(String providerSubscriptionId);

  @Query("SELECT s FROM Subscription s WHERE s.status = 'ACTIVE' AND s.currentPeriodEnd < :date")
  List<ESubscription> findActiveSubscriptionsToRenew(@Param("date") Instant date);

  List<ESubscription> findByPlanId(String planId);

  @Query("SELECT s FROM Subscription s WHERE s.status = 'TRIALING' AND s.trialEnd < :date")
  List<ESubscription> findTrialingSubscriptionsToConvert(@Param("date") Instant date);
}
