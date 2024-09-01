package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.Status;
import com.fooddeliveryfinalproject.converter.MenuCategoryConverter;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.model.IdDto;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.service.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MenuCategoryController {
    @Autowired
    private MenuCategoryService service;
    private MenuCategoryConverter converter = new MenuCategoryConverter();

    @PostMapping(value = "/menu/category/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public IdDto createMenuCategory(@RequestBody MenuCategoryDto menuCategoryDto) {
        return new IdDto(this.service.createMenuCategory(this.converter.convertToEntity(menuCategoryDto, new MenuCategory())).getCategoryId());
    }

    @GetMapping(value = "/menu/category/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuCategoryDto getMenuCategoryById(@PathVariable long id) {
        return this.converter.convertToModel(this.service.getMenuCategoryById(id), new MenuCategoryDto());
    }

    @PutMapping("/menu/category/update")
    public String updateMenuCategory(@RequestBody MenuCategoryDto menuCategoryDto) {
        this.service.updateMenuCategory(this.converter.convertToEntity(menuCategoryDto, new MenuCategory()));
        return "status: " + Status.UPDATED;
    }

    @DeleteMapping("/menu/category/delete/{id}")
    public String deleteMenuCategory(@PathVariable long id) {
        this.service.deleteMenuCategory(id);
        return "status: " + Status.DELETED;
    }
}
