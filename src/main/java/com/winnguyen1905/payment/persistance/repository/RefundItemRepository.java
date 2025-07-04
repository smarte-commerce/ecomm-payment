package com.winnguyen1905.payment.persistance.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.ERefundItem;

@Repository
public interface RefundItemRepository extends JpaRepository<ERefundItem, UUID> {
    
    List<ERefundItem> findByRefundId(UUID refundId);
    
    List<ERefundItem> findByPaymentItemId(UUID paymentItemId);
    
    List<ERefundItem> findByOrderItemId(Long orderItemId);
    
    @Query("SELECT SUM(ri.totalAmount) FROM RefundItem ri WHERE ri.refund.id = :refundId")
    Double sumTotalAmountByRefundId(@Param("refundId") UUID refundId);
    
    @Query("SELECT COUNT(ri) FROM RefundItem ri WHERE ri.paymentItem.id = :paymentItemId")
    Long countByPaymentItemId(@Param("paymentItemId") UUID paymentItemId);
} 
