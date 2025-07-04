package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_disputes")
public class EPaymentDispute extends EBaseAudit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private EPayment payment;

    @Column(name = "dispute_number", nullable = false, unique = true)
    private String disputeNumber;

    @Column(name = "provider_dispute_id")
    private String providerDisputeId;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "dispute_type", nullable = false)
    private DisputeType disputeType;

    @Column(name = "reason_code")
    private String reasonCode;

    @Column(name = "reason_description", columnDefinition = "TEXT")
    private String reasonDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DisputeStatus status;

    @Column(name = "evidence_due_by")
    private Instant evidenceDueBy;

    @Column(name = "evidence_submitted")
    private Boolean evidenceSubmitted;

    @Column(name = "evidence_details", columnDefinition = "json")
    private String evidenceDetails;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    public enum DisputeType {
        CHARGEBACK, INQUIRY, RETRIEVAL_REQUEST, PRE_ARBITRATION, ARBITRATION
    }

    public enum DisputeStatus {
        WARNING_NEEDS_RESPONSE, WARNING_UNDER_REVIEW, WARNING_CLOSED, 
        NEEDS_RESPONSE, UNDER_REVIEW, CHARGE_REFUNDED, WON, LOST
    }
} 
