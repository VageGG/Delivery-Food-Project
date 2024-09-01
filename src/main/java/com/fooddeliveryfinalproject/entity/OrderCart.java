package com.fooddeliveryfinalproject.entity;

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
public class OrderCart {
    @Id
    @GeneratedValue
    private Long cartId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToMany
    @JoinColumn(name = "menu_item_id")
    private List<MenuItem> items;
    private Integer count;
}
