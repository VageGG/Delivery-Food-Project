package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CartItemId.class)
@Table(name = "cart_items")
public class CartItem {

    @Id
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Id
    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;
}
