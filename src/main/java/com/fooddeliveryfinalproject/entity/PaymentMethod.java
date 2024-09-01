package com.fooddeliveryfinalproject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_methods")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method_type")
    private Payment.PaymentMethodType paymentMethodType;

    private String details;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
