package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Stores platform commission rates and fee structures per vendor/category.
 * Defines the rules for calculating commission on orders and settlements.
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "commission_rules", indexes = {
    @Index(name = "idx_commission_vendor_id", columnList = "vendor_id"),
    @Index(name = "idx_commission_category_id", columnList = "category_id"),
    @Index(name = "idx_commission_rule_type", columnList = "rule_type"),
    @Index(name = "idx_commission_effective_date", columnList = "effective_date")
})
public class CommissionRule extends BaseAuditEntity {

    @Column(name = "vendor_id")
    private Long vendorId;

    @Column(name = "category_id")
    private Long categoryId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "rule_name", nullable = false)
    private String ruleName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false)
    private RuleType ruleType;

    @NotNull
    @PositiveOrZero
    @Column(name = "commission_rate", nullable = false, precision = 7, scale = 4)
    private BigDecimal commissionRate;

    @Column(name = "fixed_fee_amount", precision = 10, scale = 4)
    private BigDecimal fixedFeeAmount;

    @Size(min = 3, max = 3)
    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "min_commission_amount", precision = 10, scale = 4)
    private BigDecimal minCommissionAmount;

    @Column(name = "max_commission_amount", precision = 10, scale = 4)
    private BigDecimal maxCommissionAmount;

    @Column(name = "tier_structure", columnDefinition = "json")
    private String tierStructure;

    @NotNull
    @Column(name = "effective_date", nullable = false)
    private LocalDateTime effectiveDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "additional_fees", columnDefinition = "json")
    private String additionalFees;

    /**
     * Commission rule type enumeration
     */
    public enum RuleType {
        FLAT_PERCENTAGE,      // Flat percentage of order value
        TIERED_PERCENTAGE,    // Percentage varies by order value tier
        FLAT_FEE,             // Fixed amount per order
        PERCENTAGE_PLUS_FEE,  // Combination of percentage and fixed fee
        CATEGORY_SPECIFIC,    // Rates specific to product categories
        VOLUME_BASED          // Rates based on seller's monthly volume
    }
} 
