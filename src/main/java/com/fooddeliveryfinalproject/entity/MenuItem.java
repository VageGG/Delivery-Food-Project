package com.fooddeliveryfinalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "menu_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private Long menuItemId;

    @ManyToOne
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

    @OneToMany(mappedBy = "menuItem")
    @JsonIgnore
    private List<CartItem> carts;

    @OneToMany(mappedBy = "menuItem")
    @JsonIgnore
    private List<OrderItem> orders;

    private String name;

    private Double price;

    private String description;

}