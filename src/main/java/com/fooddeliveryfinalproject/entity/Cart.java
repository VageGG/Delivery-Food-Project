package com.fooddeliveryfinalproject.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long cartId;

    @OneToMany(mappedBy = "cart")
    @JsonManagedReference
    private List<CartItem> items;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Integer count;
}
