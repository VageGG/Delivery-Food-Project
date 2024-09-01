package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "restaurantBraches")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RestaurantBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rest_branch_id")
    private Long restBrachId;

    @ManyToOne
    @JoinColumn(name = "reastaurant_id")
    private Restaurant restaurant;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private String location;

    private String phoneNumber;

}
