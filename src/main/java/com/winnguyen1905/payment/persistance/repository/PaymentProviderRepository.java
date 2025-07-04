package com.winnguyen1905.payment.persistance.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EPaymentProvider;
import com.winnguyen1905.payment.persistance.entity.EPaymentProvider.ProviderType;

@Repository
public interface PaymentProviderRepository extends JpaRepository<EPaymentProvider, UUID> {
    
    Optional<EPaymentProvider> findByProviderCode(String providerCode);
    
    List<EPaymentProvider> findByIsActiveTrue();
    
    List<EPaymentProvider> findByProviderType(ProviderType providerType);
    
    List<EPaymentProvider> findByProviderTypeAndIsActiveTrue(ProviderType providerType);
} 
