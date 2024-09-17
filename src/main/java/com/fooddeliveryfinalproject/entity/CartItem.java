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
    @Column(name = "cart_id", insertable = false, updatable = false)
    private Long cartId;

    @Id
    @Column(name = "menu_item_id", insertable = false, updatable = false)
    private Long menuItemId;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("menuItemId")
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    public CartItem(Cart cart, MenuItem menuItem) {
        this.cartId = cart.getCartId();
        this.menuItemId = menuItem.getMenuItemId();
        this.cart = cart;
        this.menuItem = menuItem;
    }
}
