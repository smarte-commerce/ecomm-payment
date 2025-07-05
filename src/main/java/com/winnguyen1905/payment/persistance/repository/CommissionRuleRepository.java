package com.winnguyen1905.payment.persistance.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.CommissionRule;
import com.winnguyen1905.payment.persistance.entity.CommissionRule.RuleType;

@Repository
public interface CommissionRuleRepository extends JpaRepository<CommissionRule, UUID> {

  List<CommissionRule> findByVendorId(Long vendorId);

  List<CommissionRule> findByCategoryId(Long categoryId);

  List<CommissionRule> findByRuleType(RuleType ruleType);

  List<CommissionRule> findByIsActiveTrue();

  List<CommissionRule> findByVendorIdAndCategoryId(Long vendorId, Long categoryId);

  @Query("SELECT cr FROM CommissionRule cr WHERE cr.effectiveDate <= :effectiveDate AND (cr.expirationDate IS NULL OR cr.expirationDate > :effectiveDate) AND cr.isActive = true")
  List<CommissionRule> findEffectiveRulesAt(@Param("effectiveDate") LocalDateTime effectiveDate);

  @Query("SELECT cr FROM CommissionRule cr WHERE (cr.vendorId = :vendorId OR cr.vendorId IS NULL) AND (cr.categoryId = :categoryId OR cr.categoryId IS NULL) AND cr.effectiveDate <= :effectiveDate AND (cr.expirationDate IS NULL OR cr.expirationDate > :effectiveDate) AND cr.isActive = true ORDER BY cr.priority DESC, cr.vendorId DESC NULLS LAST, cr.categoryId DESC NULLS LAST")
  List<CommissionRule> findBestApplicableRules(@Param("vendorId") Long vendorId, @Param("categoryId") Long categoryId,
      @Param("effectiveDate") LocalDateTime effectiveDate);

  @Query("SELECT COUNT(cr) > 0 FROM CommissionRule cr WHERE (cr.vendorId = :vendorId OR cr.vendorId IS NULL) AND (cr.categoryId = :categoryId OR cr.categoryId IS NULL) AND cr.effectiveDate <= :expirationDate AND (cr.expirationDate IS NULL OR cr.expirationDate > :effectiveDate) AND cr.isActive = true AND (:excludeRuleId IS NULL OR cr.id != :excludeRuleId)")
  boolean existsConflictingRules(@Param("vendorId") Long vendorId, @Param("categoryId") Long categoryId,
      @Param("effectiveDate") LocalDateTime effectiveDate, @Param("expirationDate") LocalDateTime expirationDate,
      @Param("excludeRuleId") UUID excludeRuleId);

  @Query("SELECT cr FROM CommissionRule cr WHERE cr.expirationDate IS NOT NULL AND cr.expirationDate < :beforeDate AND cr.isActive = true")
  List<CommissionRule> findRulesExpiringSoon(@Param("beforeDate") LocalDateTime beforeDate);
}
