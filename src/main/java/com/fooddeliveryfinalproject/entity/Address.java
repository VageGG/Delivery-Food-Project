package com.fooddeliveryfinalproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private String country;

    @NotNull
    private String city;

    private String state;

    @NotNull
    private String street;

    @NotNull
    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<CustomerAddress> customerAddresses;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private RestaurantBranch restaurantBranch;

    @OneToMany(mappedBy = "pickupLocation")
    @JsonIgnore
    private List<Delivery> pickupLocation;

    @OneToMany(mappedBy = "dropoffLocation")
    @JsonIgnore
    private List<Delivery> dropoffLocation;
}
