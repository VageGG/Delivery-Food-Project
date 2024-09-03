package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.repository.MenuCategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuCategoryService {
    @Autowired
    private MenuCategoryRepo repo;

    @Transactional
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

    @Transactional
    public MenuCategory updateMenuCategory(MenuCategory menuCategory) {
        getMenuCategoryById(menuCategory.getCategoryId());
        return this.repo.save(menuCategory);
    }

    @Transactional
    public void deleteMenuCategory(long id) {
        MenuCategory menuCategory = getMenuCategoryById(id);

        this.repo.delete(menuCategory);
    }
}
