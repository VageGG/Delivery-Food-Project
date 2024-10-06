package com.fooddeliveryfinalproject.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuCategoryDto {

    private Long categoryId;

    @NotNull
    private String name;

    private List<MenuItemDto> items;

}
