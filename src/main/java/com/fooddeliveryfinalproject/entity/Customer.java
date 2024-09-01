package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods;

    @Builder
    public Customer(Long id, String username, String password, String email, String phoneNumber, List<Order> orders,
                    List<Address> addresses, List<PaymentMethod> paymentMethods) {
        super(id, username, password, email, phoneNumber, Role.CUSTOMER);
        this.orders = orders;
        this.addresses = addresses;
        this.paymentMethods = paymentMethods;
    }
}
