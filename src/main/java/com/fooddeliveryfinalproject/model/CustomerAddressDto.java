package com.fooddeliveryfinalproject.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressDto {

    @NotNull
    private Long customerId;

    @NotNull
    private Long addressId;
}
