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
@IdClass(OrderItemId.class)
@Table(name = "order_items")
public class OrderItem {

    @Id
    @Column(name = "order_id", insertable = false, updatable = false)
    @JsonIgnore
    private Long orderId;

    @Id
    @Column(name = "menu_item_id", insertable = false, updatable = false)
    @JsonIgnore
    private Long menuItemId;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("menuItemId")
    @JoinColumn(name = "menu_item_id")
    @JsonManagedReference
    private MenuItem menuItem;

    public OrderItem(Order order, MenuItem menuItem) {
        this.orderId = order.getOrderId();
        this.menuItemId = menuItem.getMenuItemId();
        this.order = order;
        this.menuItem = menuItem;
    }
}
