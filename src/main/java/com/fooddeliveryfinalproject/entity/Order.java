package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private long orderId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "order")
    @JoinColumn(name = "order_cart_id")
    private OrderCart orderCart;

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        PREPARED,
        PICKED_UP,
        DELIVERED
    }
}
