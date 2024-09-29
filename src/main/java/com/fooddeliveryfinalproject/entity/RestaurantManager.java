package com.fooddeliveryfinalproject.entity;


import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "restaurant_managers")
public class RestaurantManager extends User{

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @OneToOne(mappedBy = "restaurantManager")
    private Restaurant restaurant;

    @Builder
    public RestaurantManager(Long id, String username, String password, String email, String phoneNumber,
                             Restaurant restaurant) {
        super(id, username, password, email, phoneNumber, Role.RESTAURANT_MANAGER);
        this.restaurant = restaurant;
    }
}
