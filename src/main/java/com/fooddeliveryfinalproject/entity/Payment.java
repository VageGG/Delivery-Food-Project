package com.fooddeliveryfinalproject.entity;

import com.fooddeliveryfinalproject.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue
    private long paymentId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @Column(name = "total")
    private double totalAmount;
    private PaymentMethod paymentMethod;
}
