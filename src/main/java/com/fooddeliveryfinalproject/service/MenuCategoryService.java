package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.MenuCategoryConverter;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.repository.MenuCategoryRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class MenuCategoryService {

    private final MenuCategoryRepo menuCategoryRepo;

    private final MenuCategoryConverter menuCategoryConverter;

    @Autowired
    public MenuCategoryService(MenuCategoryRepo menuCategoryRepo, MenuCategoryConverter menuCategoryConverter){
        this.menuCategoryRepo = menuCategoryRepo;
        this.menuCategoryConverter = menuCategoryConverter;
    }

    @Transactional
    public MenuCategory createMenuCategory(@Valid MenuCategory menuCategory) {
        return this.menuCategoryRepo.save(menuCategory);
    }

    @Transactional(readOnly = true)
    public MenuCategory getMenuCategoryById(@Min(1) long id) {
        MenuCategory menuCategory = this.menuCategoryRepo.findById(id).get();
        if (menuCategory == null) {
            throw new RuntimeException("menu category not found");
        }

        return menuCategory;
    }

    @Transactional
    public MenuCategory updateMenuCategory(@Valid MenuCategory menuCategory) {
        getMenuCategoryById(menuCategory.getCategoryId());
        return this.menuCategoryRepo.save(menuCategory);
    }

    @Transactional
    public void deleteMenuCategory(@Min(1) long id) {
        MenuCategory menuCategory = getMenuCategoryById(id);

        this.menuCategoryRepo.delete(menuCategory);
    }

    @Transactional
    public List<MenuCategory> getMenuCategoryByMenuId (@Min(1) long branchId) {
        return this.menuCategoryRepo.findAllByMenu_RestaurantBranch_RestBranchId(branchId);
    }

    @Transactional(readOnly = true)
    public List<MenuCategoryDto> getCategoriesByBranchId(@Min(1) Long branchId) {
        List<MenuCategory> categories = menuCategoryRepo.findAllByMenu_RestaurantBranch_RestBranchId(branchId);
        return categories.stream()
                .map(menuCat ->menuCategoryConverter.convertToModel(menuCat,new MenuCategoryDto()))
                .collect(Collectors.toList());
    }
}
