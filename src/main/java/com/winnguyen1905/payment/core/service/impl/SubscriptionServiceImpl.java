package com.winnguyen1905.payment.core.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winnguyen1905.payment.core.model.request.SubscriptionRequest;
import com.winnguyen1905.payment.core.model.response.SubscriptionResponse;
import com.winnguyen1905.payment.core.service.SubscriptionService;
import com.winnguyen1905.payment.exception.BadRequestException;
import com.winnguyen1905.payment.exception.ResourceNotFoundException;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod;
import com.winnguyen1905.payment.persistance.entity.EPaymentProvider;
import com.winnguyen1905.payment.persistance.entity.ESubscription;
import com.winnguyen1905.payment.persistance.entity.ESubscription.SubscriptionStatus;
import com.winnguyen1905.payment.persistance.repository.PaymentMethodRepository;
import com.winnguyen1905.payment.persistance.repository.PaymentProviderRepository;
import com.winnguyen1905.payment.persistance.repository.SubscriptionRepository;

/**
 * Implementation of SubscriptionService for managing subscriptions.
 */
@Service
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {

  @Autowired
  private SubscriptionRepository subscriptionRepository;

  @Autowired
  private PaymentMethodRepository paymentMethodRepository;

  @Autowired
  private PaymentProviderRepository paymentProviderRepository;

  @Override
  public SubscriptionResponse createSubscription(SubscriptionRequest request) {
    // Validate that payment method exists if provided
    EPaymentMethod paymentMethod = null;
    if (request.getPaymentMethodId() != null) {
      paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Payment method not found with ID: " + request.getPaymentMethodId()));
    }

    // Validate that provider exists if provided
    EPaymentProvider provider = null;
    if (request.getProviderId() != null) {
      provider = paymentProviderRepository.findById(request.getProviderId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Payment provider not found with ID: " + request.getProviderId()));
    }

    // Check if subscription number already exists
    if (subscriptionRepository.findBySubscriptionNumber(request.getSubscriptionNumber()).isPresent()) {
      throw new BadRequestException("Subscription with number " + request.getSubscriptionNumber() + " already exists");
    }

    ESubscription subscription = ESubscription.builder()
        .customerId(request.getCustomerId())
        .subscriptionNumber(request.getSubscriptionNumber())
        .paymentMethod(paymentMethod)
        .provider(provider)
        .providerSubscriptionId(request.getProviderSubscriptionId())
        .planName(request.getPlanName())
        .planId(request.getPlanId())
        .amount(request.getAmount())
        .currency(request.getCurrency())
        .intervalType(request.getIntervalType())
        .intervalCount(request.getIntervalCount())
        .trialPeriodDays(request.getTrialPeriodDays())
        .status(request.getTrialPeriodDays() != null && request.getTrialPeriodDays() > 0 ? SubscriptionStatus.TRIALING
            : SubscriptionStatus.ACTIVE)
        .currentPeriodStart(request.getCurrentPeriodStart())
        .currentPeriodEnd(request.getCurrentPeriodEnd())
        .trialStart(request.getTrialStart())
        .trialEnd(request.getTrialEnd())
        .metadata(request.getMetadata())
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  @Transactional(readOnly = true)
  public SubscriptionResponse getSubscriptionById(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));
    return mapToResponse(subscription);
  }

  @Override
  @Transactional(readOnly = true)
  public SubscriptionResponse getSubscriptionByNumber(String subscriptionNumber) {
    ESubscription subscription = subscriptionRepository.findBySubscriptionNumber(subscriptionNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with number: " + subscriptionNumber));
    return mapToResponse(subscription);
  }

  @Override
  @Transactional(readOnly = true)
  public SubscriptionResponse getSubscriptionByProviderSubscriptionId(String providerSubscriptionId) {
    ESubscription subscription = subscriptionRepository.findByProviderSubscriptionId(providerSubscriptionId)
        .orElseThrow(
            () -> new ResourceNotFoundException("Subscription not found with provider ID: " + providerSubscriptionId));
    return mapToResponse(subscription);
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubscriptionResponse> getSubscriptionsByCustomerId(Long customerId) {
    List<ESubscription> subscriptions = subscriptionRepository.findByCustomerId(customerId);
    return subscriptions.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubscriptionResponse> getSubscriptionsByStatus(SubscriptionStatus status) {
    List<ESubscription> subscriptions = subscriptionRepository.findByStatus(status);
    return subscriptions.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubscriptionResponse> getSubscriptionsByCustomerIdAndStatus(Long customerId, SubscriptionStatus status) {
    List<ESubscription> subscriptions = subscriptionRepository.findByCustomerIdAndStatus(customerId, status);
    return subscriptions.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubscriptionResponse> getSubscriptionsByPlanId(String planId) {
    List<ESubscription> subscriptions = subscriptionRepository.findByPlanId(planId);
    return subscriptions.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubscriptionResponse> getActiveSubscriptionsToRenew(Instant beforeDate) {
    List<ESubscription> subscriptions = subscriptionRepository.findActiveSubscriptionsToRenew(beforeDate);
    return subscriptions.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public List<SubscriptionResponse> getTrialingSubscriptionsToConvert(Instant beforeDate) {
    List<ESubscription> subscriptions = subscriptionRepository.findTrialingSubscriptionsToConvert(beforeDate);
    return subscriptions.stream()
        .map(this::mapToResponse)
        .collect(Collectors.toList());
  }

  @Override
  public SubscriptionResponse updateSubscription(UUID id, SubscriptionRequest request) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    // Update fields
    if (request.getPaymentMethodId() != null) {
      EPaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethodId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Payment method not found with ID: " + request.getPaymentMethodId()));
      subscription.setPaymentMethod(paymentMethod);
    }

    if (request.getProviderId() != null) {
      EPaymentProvider provider = paymentProviderRepository.findById(request.getProviderId())
          .orElseThrow(
              () -> new ResourceNotFoundException("Payment provider not found with ID: " + request.getProviderId()));
      subscription.setProvider(provider);
    }

    subscription.setPlanName(request.getPlanName());
    subscription.setPlanId(request.getPlanId());
    subscription.setAmount(request.getAmount());
    subscription.setCurrency(request.getCurrency());
    subscription.setIntervalType(request.getIntervalType());
    subscription.setIntervalCount(request.getIntervalCount());
    subscription.setTrialPeriodDays(request.getTrialPeriodDays());
    subscription.setCurrentPeriodStart(request.getCurrentPeriodStart());
    subscription.setCurrentPeriodEnd(request.getCurrentPeriodEnd());
    subscription.setTrialStart(request.getTrialStart());
    subscription.setTrialEnd(request.getTrialEnd());
    subscription.setMetadata(request.getMetadata());
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse updateSubscriptionStatus(UUID id, SubscriptionStatus status) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    subscription.setStatus(status);
    subscription.setUpdatedAt(Instant.now());

    if (status == SubscriptionStatus.CANCELLED && subscription.getCancelledAt() == null) {
      subscription.setCancelledAt(Instant.now());
    }

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse cancelSubscription(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    if (subscription.getStatus() == SubscriptionStatus.CANCELLED) {
      throw new BadRequestException("Subscription is already cancelled");
    }

    subscription.setStatus(SubscriptionStatus.CANCELLED);
    subscription.setCancelledAt(Instant.now());
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse reactivateSubscription(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    if (subscription.getStatus() != SubscriptionStatus.CANCELLED) {
      throw new BadRequestException("Only cancelled subscriptions can be reactivated");
    }

    subscription.setStatus(SubscriptionStatus.ACTIVE);
    subscription.setCancelledAt(null);
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse renewSubscription(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
      throw new BadRequestException("Only active subscriptions can be renewed");
    }

    // Calculate next billing period based on interval
    Instant nextPeriodStart = subscription.getCurrentPeriodEnd();
    Instant nextPeriodEnd = calculateNextPeriodEnd(nextPeriodStart,
        subscription.getIntervalType(), subscription.getIntervalCount());

    subscription.setCurrentPeriodStart(nextPeriodStart);
    subscription.setCurrentPeriodEnd(nextPeriodEnd);
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse convertTrialToActive(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    if (subscription.getStatus() != SubscriptionStatus.TRIALING) {
      throw new BadRequestException("Only trialing subscriptions can be converted to active");
    }

    subscription.setStatus(SubscriptionStatus.ACTIVE);
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse updatePaymentMethod(UUID id, UUID paymentMethodId) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    EPaymentMethod paymentMethod = paymentMethodRepository.findById(paymentMethodId)
        .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with ID: " + paymentMethodId));

    subscription.setPaymentMethod(paymentMethod);
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse updateSubscriptionMetadata(UUID id, String metadata) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    subscription.setMetadata(metadata);
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse pauseSubscription(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    if (subscription.getStatus() != SubscriptionStatus.ACTIVE) {
      throw new BadRequestException("Only active subscriptions can be paused");
    }

    subscription.setStatus(SubscriptionStatus.UNPAID);
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public SubscriptionResponse resumeSubscription(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    if (subscription.getStatus() != SubscriptionStatus.UNPAID) {
      throw new BadRequestException("Only paused (unpaid) subscriptions can be resumed");
    }

    subscription.setStatus(SubscriptionStatus.ACTIVE);
    subscription.setUpdatedAt(Instant.now());

    subscription = subscriptionRepository.save(subscription);
    return mapToResponse(subscription);
  }

  @Override
  public void deleteSubscription(UUID id) {
    ESubscription subscription = subscriptionRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with ID: " + id));

    subscriptionRepository.delete(subscription);
  }

  /**
   * Maps ESubscription entity to SubscriptionResponse DTO
   */
  private SubscriptionResponse mapToResponse(ESubscription subscription) {
    return SubscriptionResponse.builder()
        .id(subscription.getId())
        .customerId(subscription.getCustomerId())
        .subscriptionNumber(subscription.getSubscriptionNumber())
        .paymentMethodId(subscription.getPaymentMethod() != null ? subscription.getPaymentMethod().getId() : null)
        .paymentMethodDisplayName(
            subscription.getPaymentMethod() != null ? subscription.getPaymentMethod().getDisplayName() : null)
        .providerId(subscription.getProvider() != null ? subscription.getProvider().getId() : null)
        .providerName(subscription.getProvider() != null ? subscription.getProvider().getProviderName() : null)
        .providerSubscriptionId(subscription.getProviderSubscriptionId())
        .planName(subscription.getPlanName())
        .planId(subscription.getPlanId())
        .amount(subscription.getAmount())
        .currency(subscription.getCurrency())
        .intervalType(subscription.getIntervalType())
        .intervalCount(subscription.getIntervalCount())
        .trialPeriodDays(subscription.getTrialPeriodDays())
        .status(subscription.getStatus())
        .currentPeriodStart(subscription.getCurrentPeriodStart())
        .currentPeriodEnd(subscription.getCurrentPeriodEnd())
        .trialStart(subscription.getTrialStart())
        .trialEnd(subscription.getTrialEnd())
        .cancelledAt(subscription.getCancelledAt())
        .endedAt(subscription.getEndedAt())
        .metadata(subscription.getMetadata())
        .createdAt(subscription.getCreatedAt())
        .updatedAt(subscription.getUpdatedAt())
        .build();
  }

  /**
   * Calculates the next period end based on interval type and count
   */
  private Instant calculateNextPeriodEnd(Instant start, ESubscription.IntervalType intervalType,
      Integer intervalCount) {
    switch (intervalType) {
      case DAY:
        return start.plusSeconds(intervalCount * 24 * 60 * 60);
      case WEEK:
        return start.plusSeconds(intervalCount * 7 * 24 * 60 * 60);
      case MONTH:
        return start.plusSeconds(intervalCount * 30 * 24 * 60 * 60); // Approximation
      case YEAR:
        return start.plusSeconds(intervalCount * 365 * 24 * 60 * 60); // Approximation
      default:
        throw new BadRequestException("Invalid interval type: " + intervalType);
    }
  }
}
