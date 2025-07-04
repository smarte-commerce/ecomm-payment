package com.winnguyen1905.payment.persistance.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EPayment;
import com.winnguyen1905.payment.persistance.entity.EPayment.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<EPayment, UUID> {

    Optional<EPayment> findByPaymentNumber(String paymentNumber);
    
    List<EPayment> findByOrderId(Long orderId);
    
    List<EPayment> findByCustomerId(Long customerId);
    
    List<EPayment> findByStatus(PaymentStatus status);
    
    @Query("SELECT p FROM Payment p WHERE p.orderId = :orderId AND p.status = :status")
    List<EPayment> findByOrderIdAndStatus(@Param("orderId") Long orderId, @Param("status") PaymentStatus status);
    
    List<EPayment> findByCustomerIdAndStatus(Long customerId, PaymentStatus status);
    
    @Query("SELECT p FROM Payment p WHERE p.transactionId = :transactionId")
    Optional<EPayment> findByTransactionId(@Param("transactionId") String transactionId);
}
