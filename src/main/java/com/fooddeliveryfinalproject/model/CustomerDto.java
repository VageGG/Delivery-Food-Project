package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {

    private Long id;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private User.Role role;

    private List<OrderDto> ordersDto;

    private List<CustomerAddressDto> addressesDto;

    private CartDto cartDto;

    private List<PaymentMethodDto> paymentMethodsDto;
}
