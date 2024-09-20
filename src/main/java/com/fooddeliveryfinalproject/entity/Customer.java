package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
public class Customer extends User {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerAddress> addresses;

    @OneToOne(mappedBy = "customer")
    private Cart cart;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToMany(mappedBy = "customer")
    private List<PaymentMethod> paymentMethods;

    @Builder
    public Customer(Long id, String username, String password, String email, String phoneNumber, List<Order> orders,
                    List<CustomerAddress> addresses, List<PaymentMethod> paymentMethods) {
        super(id, username, password, email, phoneNumber, Role.CUSTOMER);
        this.orders = orders;
        this.addresses = addresses;
        this.paymentMethods = paymentMethods;
    }
}
