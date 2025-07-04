package com.winnguyen1905.payment.persistance.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "payment_items")
public class EPaymentItem extends EBaseAudit {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private EPayment payment;

    @Column(name = "order_item_id")
    private Long orderItemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitAmount;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "metadata", columnDefinition = "json")
    private String metadata;

    @Column(name = "created_at")
    private Instant createdAt;

    @OneToMany(mappedBy = "paymentItem")
    private List<ERefundItem> refundItems;

    public enum ItemType {
        PRODUCT, SHIPPING, TAX, DISCOUNT, FEE
    }
} 
