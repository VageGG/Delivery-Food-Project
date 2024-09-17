package com.fooddeliveryfinalproject.entity;

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
    private Long orderId;

    @Id
    @Column(name = "menu_item_id", insertable = false, updatable = false)
    private Long menuItemId;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("menuItemId")
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    public OrderItem(Order order, MenuItem menuItem) {
        this.orderId = order.getOrderId();
        this.menuItemId = menuItem.getMenuItemId();
        this.order = order;
        this.menuItem = menuItem;
    }
}
