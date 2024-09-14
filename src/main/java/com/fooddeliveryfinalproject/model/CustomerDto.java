package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class CustomerDto extends UserDto {


    private List<OrderDto> ordersDto;

    private List<CustomerAddressDto> addressesDto;

    private CartDto cartDto;

    private List<PaymentMethodDto> paymentMethodsDto;
}
