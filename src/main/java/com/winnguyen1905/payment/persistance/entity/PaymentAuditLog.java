package com.winnguyen1905.payment.persistance.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Comprehensive audit trail for all payment-related actions.
 * Tracks all modifications, status changes, and admin actions on payment entities.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_audit_logs", indexes = {
    @Index(name = "idx_audit_entity_type", columnList = "entity_type"),
    @Index(name = "idx_audit_entity_id", columnList = "entity_id"),
    @Index(name = "idx_audit_action_type", columnList = "action_type"),
    @Index(name = "idx_audit_action_timestamp", columnList = "action_timestamp"),
    @Index(name = "idx_audit_user_id", columnList = "user_id"),
    @Index(name = "idx_audit_transaction_id", columnList = "transaction_id")
})
public class PaymentAuditLog extends BaseAuditEntity {

    @NotBlank
    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @NotBlank
    @Column(name = "entity_type", nullable = false)
    private String entityType;

    @NotBlank
    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false)
    private ActionType actionType;

    @NotNull
    @Column(name = "action_timestamp", nullable = false)
    private LocalDateTime actionTimestamp;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "previous_state", columnDefinition = "json")
    private String previousState;

    @Column(name = "new_state", columnDefinition = "json")
    private String newState;

    @Column(name = "changed_fields", columnDefinition = "json")
    private String changedFields;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    /**
     * Action type enumeration
     */
    public enum ActionType {
        CREATE,                // Entity creation
        UPDATE,                // Entity update
        DELETE,                // Entity deletion
        STATUS_CHANGE,         // Status change
        PAYMENT_INITIATE,      // Payment initiation
        PAYMENT_AUTHORIZE,     // Payment authorization
        PAYMENT_CAPTURE,       // Payment capture
        PAYMENT_COMPLETE,      // Payment completion
        PAYMENT_FAIL,          // Payment failure
        REFUND_REQUEST,        // Refund request
        REFUND_APPROVE,        // Refund approval
        REFUND_REJECT,         // Refund rejection
        REFUND_PROCESS,        // Refund processing
        ESCROW_HOLD,           // Escrow funds hold
        ESCROW_RELEASE,        // Escrow funds release
        SETTLEMENT_CALCULATE,  // Settlement calculation
        SETTLEMENT_PAY,        // Settlement payment
        ADMIN_OVERRIDE,        // Admin override action
        SYSTEM_EVENT           // Automated system event
    }

    /**
     * User type enumeration
     */
    public enum UserType {
        CUSTOMER,      // End customer
        VENDOR,        // Platform vendor
        ADMIN,         // Platform administrator
        SYSTEM,        // Automated system process
        API            // External API call
    }
} 
