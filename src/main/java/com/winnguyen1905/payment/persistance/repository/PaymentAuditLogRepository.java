package com.winnguyen1905.payment.persistance.repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EPaymentAuditLog;
import com.winnguyen1905.payment.persistance.entity.EPaymentAuditLog.ActionType;
import com.winnguyen1905.payment.persistance.entity.EPaymentAuditLog.UserType;

@Repository
public interface PaymentAuditLogRepository extends JpaRepository<EPaymentAuditLog, UUID> {
    
    List<EPaymentAuditLog> findByPaymentId(UUID paymentId);
    
    List<EPaymentAuditLog> findByRefundId(UUID refundId);
    
    List<EPaymentAuditLog> findBySubscriptionId(UUID subscriptionId);
    
    List<EPaymentAuditLog> findByActionType(ActionType actionType);
    
    List<EPaymentAuditLog> findByUserId(Long userId);
    
    List<EPaymentAuditLog> findByUserType(UserType userType);
    
    Page<EPaymentAuditLog> findByCreatedAtBetween(Instant startDate, Instant endDate, Pageable pageable);
    
    @Query("SELECT pal FROM PaymentAuditLog pal WHERE pal.actionType IN :actionTypes AND pal.createdAt > :date")
    List<EPaymentAuditLog> findRecentActivityByActionTypes(
        @Param("actionTypes") List<ActionType> actionTypes, 
        @Param("date") Instant date);
    
    @Query("SELECT COUNT(pal) FROM PaymentAuditLog pal WHERE pal.actionType = :actionType AND pal.createdAt > :date")
    Long countByActionTypeAndDateAfter(
        @Param("actionType") ActionType actionType, 
        @Param("date") Instant date);
} 
