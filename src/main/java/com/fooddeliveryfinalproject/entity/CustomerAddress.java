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
    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customerId;

    @Id
    @Column(name = "address_id", insertable = false, updatable = false)
    private Long addressId;

    @ManyToOne
    @MapsId("customerId")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("addressId")
    @JoinColumn(name = "address_id")
    private Address address;

    public CustomerAddress(Customer customer, Address address) {
        this.customer = customer;
        this.address = address;
    }
}
