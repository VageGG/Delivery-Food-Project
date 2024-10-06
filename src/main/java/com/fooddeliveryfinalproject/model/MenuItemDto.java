package com.fooddeliveryfinalproject.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDto {

    private Long menuItemId;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    private String description;

}
