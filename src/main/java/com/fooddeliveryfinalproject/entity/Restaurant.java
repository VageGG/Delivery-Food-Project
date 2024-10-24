package com.fooddeliveryfinalproject.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "restaurant_name", nullable = false, unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private RestaurantManager restaurantManager;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<RestaurantBranch> branches;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
