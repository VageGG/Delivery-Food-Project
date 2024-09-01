package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.repository.MenuCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuCategoryService {
    @Autowired
    private MenuCategoryRepo repo;

    public MenuCategory createMenuCategory(MenuCategory menuCategory) {
        return this.repo.save(menuCategory);
    }

    public MenuCategory getMenuCategoryById(long id) {
        MenuCategory menuCategory = this.repo.findById(id).get();
        if (menuCategory == null) {
            throw new RuntimeException("menu category not found");
        }

        return menuCategory;
    }

    public MenuCategory updateMenuCategory(MenuCategory menuCategory) {
        getMenuCategoryById(menuCategory.getCategoryId());
        return this.repo.save(menuCategory);
    }

    public void deleteMenuCategory(long id) {
        MenuCategory menuCategory = getMenuCategoryById(id);

        this.repo.delete(menuCategory);
    }
}
