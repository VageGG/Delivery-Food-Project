package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "menus")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @OneToOne(mappedBy = "menu")
    private RestaurantBranch restaurantBranch;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private MenuCategory menuCategory;
}

