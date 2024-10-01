package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.MenuCategoryConverter;
import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.converter.RestaurantBranchConverter;
import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.MenuCategoryDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.model.RestaurantBranchDto;
import com.fooddeliveryfinalproject.repository.MenuCategoryRepo;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import com.fooddeliveryfinalproject.repository.MenuRepo;
import com.fooddeliveryfinalproject.repository.RestaurantBranchRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantBranchService {
    private final RestaurantBranchRepo restaurantBranchRepo;

    private final RestaurantBranchConverter restaurantBranchConverter;

    private final MenuRepo menuRepo;

    private final MenuCategoryRepo menuCategoryRepo;

    private final MenuCategoryConverter menuCategoryConverter;

    private final MenuItemRepo menuItemRepo;

    private final MenuItemConverter menuItemConverter;


    @Autowired
    public RestaurantBranchService(RestaurantBranchRepo restaurantBranchRepo,
                                   RestaurantBranchConverter restaurantBranchConverter,
                                   MenuRepo menuRepo,
                                   MenuCategoryRepo menuCategoryRepo,
                                   MenuCategoryConverter menuCategoryConverter,
                                   MenuItemRepo menuItemRepo,
                                   MenuItemConverter menuItemConverter) {
        this.restaurantBranchRepo = restaurantBranchRepo;
        this.restaurantBranchConverter = restaurantBranchConverter;
        this.menuRepo = menuRepo;
        this.menuCategoryRepo = menuCategoryRepo;
        this.menuCategoryConverter = menuCategoryConverter;
        this.menuItemRepo = menuItemRepo;
        this.menuItemConverter = menuItemConverter;
    }

    public RestaurantBranchDto getRestaurantBranch(Long id) {
        return restaurantBranchConverter.convertToModel(restaurantBranchRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Could not find RestaurantBranch")),
                new RestaurantBranchDto());
    }

    public Page<RestaurantBranchDto> getAllRestaurantBranches(Long restaurantId, Pageable pageable) {
        if (restaurantId == null) {
            throw new RuntimeException("Restaurant ID is required");
        }

        Page<RestaurantBranch> branches = restaurantBranchRepo.findAllByRestaurantId(restaurantId, pageable);
        return branches.map(restaurantBranch -> restaurantBranchConverter.convertToModel(restaurantBranch, new RestaurantBranchDto()));
    }


    @Transactional
    public void createRestaurantBranch(RestaurantBranchDto restaurantBranchDto) {

        RestaurantBranch restaurantBranch = restaurantBranchConverter.convertToEntity(restaurantBranchDto, new RestaurantBranch());

        Menu menu = new Menu();
        menu.setRestaurantBranch(restaurantBranch);
        restaurantBranch.setMenu(menu);

        menuRepo.save(menu);
        restaurantBranchRepo.save(restaurantBranch);
    }

    @Transactional
    public void updateRestaurantBranch(Long id, RestaurantBranchDto restaurantBranchDto) {
        RestaurantBranch restaurantBranchEntity = restaurantBranchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find RestaurantBranch"));
        restaurantBranchConverter.convertToEntity(restaurantBranchDto, restaurantBranchEntity);
        restaurantBranchRepo.save(restaurantBranchEntity);

    }

    @Transactional
    public void deleteRestaurantBranch(Long id) {
        restaurantBranchRepo.deleteById(id);
    }


    // Այս մեթոդը գրել այստեղ թե MenuCategoryService-ի մեջ
    @Transactional(readOnly = true)
    public List<MenuCategoryDto> getAllCategoriesByBranchId(Long branchId) {

        if (branchId == null) {
            throw new RuntimeException("Branch ID is required");
        }

        List<MenuCategory> menuCategories = menuCategoryRepo.findAllByMenu_RestaurantBranch_RestBranchId(branchId);

        if (menuCategories.isEmpty()) {
            throw new RuntimeException("No categories found for this branch");
        }

        return menuCategoryConverter.convertToModelList(menuCategories, MenuCategoryDto::new);
    }


//    @Transactional(readOnly = true)
//    public List<MenuItemDto> getMenuItemsByCategoryId(Long branchId, Long categoryId) {
//
//        List<MenuItem> menuItems = menuItemRepo.findByMenuCategoryIdAndRestaurantBranchId(categoryId, branchId);
//
//        if (menuItems.isEmpty()) {
//            throw new RuntimeException("No menu items found for this branch and category");
//        }
//
//        return menuItemConverter.convertToModelList(menuItems, MenuItemDto::new);
//
//    }

    @Transactional
    public void addMenuCategoryToBranch(Long branchId, MenuCategoryDto menuCategoryDto) {

        Menu menu = menuRepo.findByRestaurantBranchId(branchId);

        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }

        MenuCategory menuCategory = menuCategoryConverter.convertToEntity(menuCategoryDto,new MenuCategory());
        menuCategory.setMenu(menu);
        menuCategoryRepo.save(menuCategory);
    }


    @Transactional
    public void deleteMenuCategoryFromBranch(Long branchId, Long categoryId) {

        Menu menu = menuRepo.findByRestaurantBranchId(branchId);

        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }

        MenuCategory menuCategory = menuCategoryRepo.findByIdAndMenu(categoryId, menu)
                .orElseThrow(() -> new RuntimeException("Menu Category with ID " + categoryId + " not found"));

        menuCategoryRepo.delete(menuCategory);
    }

    public void addMenuItemToCategory(Long branchId, Long categoryId, MenuItemDto menuItemDto) {

        Menu menu = menuRepo.findByRestaurantBranchId(branchId);
        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }
        MenuCategory menuCategory = menuCategoryRepo.findByIdAndMenu(categoryId, menu)
                .orElseThrow(() -> new RuntimeException("Menu Category with ID " + categoryId + " not found"));

        MenuItem menuItem = menuItemConverter.convertToEntity(menuItemDto,new MenuItem());

        menuItem.setMenuCategory(menuCategory);

        menuItemRepo.save(menuItem);
    }

    public void deleteMenuItemFromCategory(Long branchId, Long categoryId, Long itemId) {

        Menu menu = menuRepo.findByRestaurantBranchId(branchId);
        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }

        MenuCategory menuCategory = menuCategoryRepo.findByIdAndMenu(categoryId, menu)
                .orElseThrow(() -> new RuntimeException("Menu Category with ID " + categoryId + " not found"));

        MenuItem menuItem = menuItemRepo.findByIdAndMenuCategory(itemId, menuCategory)
                .orElseThrow(() -> new RuntimeException("Menu Item with ID " + itemId + " not found"));

        menuItemRepo.delete(menuItem);
    }

}