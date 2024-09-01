package com.fooddeliveryfinalproject.entity;

import com.fooddeliveryfinalproject.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue
    private long orderId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToMany(mappedBy = "order")
    private List<MenuItem> items;
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private OrderStatus status;
}
