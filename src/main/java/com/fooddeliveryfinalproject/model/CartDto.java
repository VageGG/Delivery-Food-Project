package com.fooddeliveryfinalproject.model;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CartDto {

    private Long cartId;

    private List<CartItemDto> cartItems;

    @Min(1)
    private Integer count;
}
