package com.winnguyen1905.payment.persistance.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EFraudCheck;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckStatus;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.CheckType;
import com.winnguyen1905.payment.persistance.entity.EFraudCheck.RiskLevel;

@Repository
public interface FraudCheckRepository extends JpaRepository<EFraudCheck, UUID> {

  List<EFraudCheck> findByPaymentId(UUID paymentId);

  List<EFraudCheck> findByOrderId(Long orderId);

  List<EFraudCheck> findByCustomerId(Long customerId);

  List<EFraudCheck> findByStatus(CheckStatus status);

  List<EFraudCheck> findByRiskLevel(RiskLevel riskLevel);

  List<EFraudCheck> findByCheckType(CheckType checkType);

  @Query("SELECT fc FROM FraudCheck fc WHERE fc.riskLevel IN :riskLevels AND fc.status = 'PENDING'")
  List<EFraudCheck> findPendingByRiskLevels(@Param("riskLevels") List<RiskLevel> riskLevels);

  @Query("SELECT AVG(fc.riskScore) FROM FraudCheck fc WHERE fc.customerId = :customerId")
  Double averageRiskScoreByCustomerId(@Param("customerId") Long customerId);
}
