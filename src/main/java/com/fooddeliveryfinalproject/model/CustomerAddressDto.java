package com.fooddeliveryfinalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDto {

    private Long customerId;

    private Long addressId;
}
