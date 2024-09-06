package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CustomerAddressId.class)
@Table(name = "customer_addresses")
public class CustomerAddress {

    @Id
    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "address_id")
    private Long addressId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
