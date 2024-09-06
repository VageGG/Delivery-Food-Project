package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    private String country;

    private String city;

    private String state;

    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<CustomerAddress> customerAddresses;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private RestaurantBranch restaurantBranch;

    @OneToMany(mappedBy = "pickupLocation", cascade = CascadeType.ALL)
    private List<Delivery> pickupLocation;

    @OneToMany(mappedBy = "dropoffLocation", cascade = CascadeType.ALL)
    private List<Delivery> dropoffLocation;
}
