package com.fooddeliveryfinalproject.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CartItemDto {

    @NotNull
    private Long cartId;

    @NotNull
    private Long menuItemId;
}
