package com.winnguyen1905.payment.persistance.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EPaymentMethod;
import com.winnguyen1905.payment.persistance.entity.EPaymentMethod.MethodType;

@Repository
public interface PaymentMethodRepository extends JpaRepository<EPaymentMethod, UUID> {

  List<EPaymentMethod> findByCustomerId(Long customerId);

  List<EPaymentMethod> findByCustomerIdAndIsActiveTrue(Long customerId);

  Optional<EPaymentMethod> findByCustomerIdAndIsDefaultTrue(Long customerId);

  List<EPaymentMethod> findByMethodType(MethodType methodType);

  Optional<EPaymentMethod> findByProviderPaymentMethodId(String providerPaymentMethodId);

  @Query("SELECT pm FROM PaymentMethod pm WHERE pm.customerId = :customerId AND pm.methodType = :methodType AND pm.isActive = true")
  List<EPaymentMethod> findActiveMethodsByCustomerAndType(
      @Param("customerId") Long customerId,
      @Param("methodType") MethodType methodType);
}
