package com.winnguyen1905.payment.persistance.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.ERefund;
import com.winnguyen1905.payment.persistance.entity.ERefund.RefundStatus;

@Repository
public interface RefundRepository extends JpaRepository<ERefund, UUID> {
    
    Optional<ERefund> findByRefundNumber(String refundNumber);
    
    List<ERefund> findByPaymentId(UUID paymentId);
    
    List<ERefund> findByOrderId(Long orderId);
    
    List<ERefund> findByStatus(RefundStatus status);
    
    Optional<ERefund> findByProviderRefundId(String providerRefundId);
} 
