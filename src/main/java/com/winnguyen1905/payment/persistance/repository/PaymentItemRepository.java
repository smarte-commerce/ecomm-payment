package com.winnguyen1905.payment.persistance.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EPaymentItem;
import com.winnguyen1905.payment.persistance.entity.EPaymentItem.ItemType;

@Repository
public interface PaymentItemRepository extends JpaRepository<EPaymentItem, UUID> {

  List<EPaymentItem> findByPaymentId(UUID paymentId);

  List<EPaymentItem> findByOrderItemId(Long orderItemId);

  List<EPaymentItem> findByItemType(ItemType itemType);

  @Query("SELECT pi FROM PaymentItem pi WHERE pi.payment.id = :paymentId AND pi.itemType = :itemType")
  List<EPaymentItem> findByPaymentIdAndItemType(
      @Param("paymentId") UUID paymentId,
      @Param("itemType") ItemType itemType);

  @Query("SELECT SUM(pi.totalAmount) FROM PaymentItem pi WHERE pi.payment.id = :paymentId AND pi.itemType = :itemType")
  Double sumTotalAmountByPaymentIdAndItemType(
      @Param("paymentId") UUID paymentId,
      @Param("itemType") ItemType itemType);
}
