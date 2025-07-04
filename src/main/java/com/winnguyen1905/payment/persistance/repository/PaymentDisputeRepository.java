package com.winnguyen1905.payment.persistance.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.winnguyen1905.payment.persistance.entity.EPaymentDispute;
import com.winnguyen1905.payment.persistance.entity.EPaymentDispute.DisputeStatus;
import com.winnguyen1905.payment.persistance.entity.EPaymentDispute.DisputeType;

@Repository
public interface PaymentDisputeRepository extends JpaRepository<EPaymentDispute, UUID> {
    
    Optional<EPaymentDispute> findByDisputeNumber(String disputeNumber);
    
    List<EPaymentDispute> findByPaymentId(UUID paymentId);
    
    List<EPaymentDispute> findByStatus(DisputeStatus status);
    
    List<EPaymentDispute> findByDisputeType(DisputeType disputeType);
    
    Optional<EPaymentDispute> findByProviderDisputeId(String providerDisputeId);
    
    @Query("SELECT pd FROM PaymentDispute pd WHERE pd.evidenceDueBy < :date AND pd.evidenceSubmitted = false")
    List<EPaymentDispute> findDisputesWithEvidenceDueSoon(@Param("date") Instant date);
    
    List<EPaymentDispute> findByStatusAndEvidenceSubmittedFalse(DisputeStatus status);
    
    @Query("SELECT COUNT(pd) FROM PaymentDispute pd WHERE pd.payment.customerId = :customerId")
    Long countDisputesByCustomerId(@Param("customerId") Long customerId);
} 
