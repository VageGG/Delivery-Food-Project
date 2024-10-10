package com.fooddeliveryfinalproject.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    private Long cartId;

    @Id
    @Column(name = "menu_item_id", insertable = false, updatable = false)
    @JsonIgnore
    private Long menuItemId;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @MapsId("menuItemId")
    @JoinColumn(name = "menu_item_id")
    @JsonManagedReference
    private MenuItem menuItem;

    public CartItem(Cart cart, MenuItem menuItem) {
        this.cart = cart;
        this.menuItem = menuItem;
    }
}
