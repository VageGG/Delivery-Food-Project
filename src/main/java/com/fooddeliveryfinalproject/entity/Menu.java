package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "menus")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    @OneToOne
    @JoinColumn(name = "rest_branch_id")
    private RestaurantBranch restaurantBranch;

    @OneToMany(mappedBy = "menu")
    private List<MenuCategory> menuCategories;
}

