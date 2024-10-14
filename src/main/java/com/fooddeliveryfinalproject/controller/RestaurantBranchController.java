package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.service.MenuService;
import com.fooddeliveryfinalproject.service.RestaurantBranchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant-branch")
@Validated
public class RestaurantBranchController {

    private final MenuService menuService;

    private final RestaurantBranchService restaurantBranchService;

    @Autowired
    public RestaurantBranchController(MenuService menuService,RestaurantBranchService restaurantBranchService){
        this.menuService = menuService;
        this.restaurantBranchService = restaurantBranchService;
    }


    @GetMapping("/menu/{restaurantBranchId}")
    public ResponseEntity<MenuDto> getMenuByRestaurantBranchId(@PathVariable("restaurantBranchId") @Min(1) Long restaurantBranchId) {
        MenuDto menuDto = menuService.getMenuByRestaurantBranchId(restaurantBranchId);
        if (menuDto != null) {
            return ResponseEntity.ok(menuDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/list/{restaurantId}")
    public ResponseEntity<List<RestaurantBranchDto>> getAllRestaurantBranches(
            @PathVariable("restaurantId") @Min(1) Long restaurantId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(10) @Max(100) int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<RestaurantBranchDto> restaurantBranchesDtos = restaurantBranchService.getAllRestaurantBranches(restaurantId, pageable)
                .stream()
                .collect(Collectors.toList());
        return ResponseEntity.ok(restaurantBranchesDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantBranchDto> getRestaurantBranch(@PathVariable("id") @Min(1) Long id) {
        return new ResponseEntity<>(restaurantBranchService.getRestaurantBranchDto(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/create/{restId}")
    public ResponseEntity<HttpStatus> createRestaurantBranch(@PathVariable("restId") @Min(1) Long id,@RequestBody @Valid RestaurantBranchDto restaurantBranchDto) {
        restaurantBranchService.createRestaurantBranch(id, restaurantBranchDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateRestaurantBranch(@PathVariable("id") @Min(1) Long id, @RequestBody @Valid RestaurantBranchDto restaurantBranchDto) {
        restaurantBranchService.updateRestaurantBranch(id, restaurantBranchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurantBranch(@PathVariable("id") @Min(1) Long id) {
        restaurantBranchService.deleteRestaurantBranch(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/categories/{branchId}")
    public List<MenuCategoryDto> getAllMenuCategoriesByBranchId(@PathVariable @Min(1) Long branchId) {
        return restaurantBranchService.getAllCategoriesByBranchId(branchId);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/add-category/{branchId}")
    public ResponseEntity<HttpStatus> addMenuCategoryToBranch(@PathVariable @Min(1) Long branchId, @RequestBody MenuCategoryDto menuCategoryDto) {
        restaurantBranchService.addMenuCategoryToBranch(branchId, menuCategoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/{branchId}/delete-category/{categoryId}")
    public ResponseEntity<HttpStatus> deleteMenuCategoryFromBranch(@PathVariable("branchId") @Min(1) Long branchId, @PathVariable("categoryId") @Min(1) Long categoryId) {
        restaurantBranchService.deleteMenuCategoryFromBranch(branchId, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/{branchId}/add-menu-item/{categoryId}")
    public ResponseEntity<HttpStatus> addMenuItemToCategory(@PathVariable @Min(1) Long branchId,
                                                            @PathVariable @Min(1) Long categoryId,
                                                            @RequestBody @Valid MenuItemDto menuItemDto) {
        restaurantBranchService.addMenuItemToCategory(branchId, categoryId, menuItemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/{branchId}/delete-menu-item/{categoryId}/{itemId}")
    public ResponseEntity<HttpStatus> deleteMenuItemFromCategory(@PathVariable("branchId") @Min(1) Long branchId,
                                                                 @PathVariable("categoryId") @Min(1) Long categoryId,
                                                                 @PathVariable("itemId") @Min(1) Long itemId) {
        restaurantBranchService.deleteMenuItemFromCategory(branchId, categoryId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/categories-with-menu-items/{branchId}")
    public ResponseEntity<List<MenuCategoryWithItemsDto>> getCategoriesWithItemsByBranchId(@PathVariable @Min(1) Long branchId) {
        List<MenuCategoryWithItemsDto> categoriesWithItems = restaurantBranchService.getCategoriesWithItemsByBranchId(branchId);
        return ResponseEntity.ok(categoriesWithItems);
    }

    @GetMapping("/category/{branchId}/{categoryId}")
    public ResponseEntity<MenuCategoryWithItemsDto> getCategoryWithItemsByBranchIdAndCategoryId(@PathVariable @Min(1) Long branchId,
                                                                                                @PathVariable @Min(1) Long categoryId) {
        MenuCategoryWithItemsDto categoryWithItems = restaurantBranchService.getCategoryWithItemsByBranchIdAndCategoryId(branchId, categoryId);
        return ResponseEntity.ok(categoryWithItems);
    }


}
