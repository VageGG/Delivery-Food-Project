package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryDto {
    private long categoryId;
    private String name;
    private List<MenuItem> items;
    private Menu menu;
}
