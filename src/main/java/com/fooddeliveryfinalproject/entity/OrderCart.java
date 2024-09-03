package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_carts")
public class OrderCart {

    @Id
    @GeneratedValue
    @Column(name = "cart_id")
    private Long cartId;

    @OneToOne(mappedBy = "orderCart")
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "orderCart")
    private List<MenuItem> items;

    private Integer count;
}
