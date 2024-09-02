package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "restaurant_branches")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RestaurantBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rest_branch_id")
    private Long restBranchId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private String location;

    @Column(name = "phone_number")
    private String phoneNumber;

}
