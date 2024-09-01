package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private long paymentId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "total_price")
    private double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type")
    private PaymentMethodType paymentMethod;

    public enum PaymentMethodType {
        CARD,
        CASH,
        IDRAM,
        PAYPAL
    }
}
