package com.fooddeliveryfinalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemDto {

    private Long cartId;

    private Long menuItemId;

    private Integer qty;
}
