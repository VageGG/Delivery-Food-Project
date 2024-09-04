package com.fooddeliveryfinalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAddressId implements Serializable {

    private Long customerId;

    private Long addressId;


}
