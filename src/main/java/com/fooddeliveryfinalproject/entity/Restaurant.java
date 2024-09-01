package com.fooddeliveryfinalproject.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "restaurants")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long restId;

    @Column(name = "restaurant_name", nullable = false)
    private String restName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private RestaurantManager restaurantManager;

    @OneToMany(mappedBy = "restaurant")
    private List<RestaurantBranch> branches;

    @Column(name = "rating")
    private Double rating;



}
