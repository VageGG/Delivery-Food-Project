package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.service.MenuService;
import com.fooddeliveryfinalproject.service.RestaurantBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant-branch")
public class RestaurantBranchController {

    private final MenuService menuService;

    private final RestaurantBranchService restaurantBranchService;

    @Autowired
    public RestaurantBranchController(MenuService menuService,RestaurantBranchService restaurantBranchService){
        this.menuService = menuService;
        this.restaurantBranchService = restaurantBranchService;
    }


    @GetMapping("/menu/{restaurantBranchId}")
    public ResponseEntity<MenuDto> getMenuByRestaurantBranchId(@PathVariable("restaurantBranchId") Long restaurantBranchId) {
        MenuDto menuDto = menuService.getMenuByRestaurantBranchId(restaurantBranchId);
        if (menuDto != null) {
            return ResponseEntity.ok(menuDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/list/{restaurantId}")
    public ResponseEntity<List<RestaurantBranchDto>> getAllRestaurantBranches(
            @PathVariable("restaurantId") Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<RestaurantBranchDto> restaurantBranchesDtos = restaurantBranchService.getAllRestaurantBranches(restaurantId, pageable)
                .stream()
                .collect(Collectors.toList());
        return ResponseEntity.ok(restaurantBranchesDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantBranchDto> getRestaurantBranch(@PathVariable("id") Long id) {
        return new ResponseEntity<>(restaurantBranchService.getRestaurantBranch(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> createRestaurantBranch(@RequestBody RestaurantBranchDto restaurantBranchDto) {
        restaurantBranchService.createRestaurantBranch(restaurantBranchDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateRestaurantBranch(@PathVariable("id") Long id, @RequestBody RestaurantBranchDto restaurantBranchDto) {
        restaurantBranchService.updateRestaurantBranch(id, restaurantBranchDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurantBranch(@PathVariable("id") Long id) {
        restaurantBranchService.deleteRestaurantBranch(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/categories/{branchId}")
    public List<MenuCategoryDto> getAllMenuCategoriesByBranchId(@PathVariable Long branchId) {
        return restaurantBranchService.getAllCategoriesByBranchId(branchId);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/add-category/{branchId}")
    public ResponseEntity<HttpStatus> addMenuCategoryToBranch(@PathVariable Long branchId, @RequestBody MenuCategoryDto menuCategoryDto) {
        restaurantBranchService.addMenuCategoryToBranch(branchId, menuCategoryDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/{branchId}/delete-category/{categoryId}")
    public ResponseEntity<HttpStatus> deleteMenuCategoryFromBranch(@PathVariable("branchId") Long branchId, @PathVariable("categoryId") Long categoryId) {
        restaurantBranchService.deleteMenuCategoryFromBranch(branchId, categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/{branchId}/add-menu-item/{categoryId}")
    public ResponseEntity<HttpStatus> addMenuItemToCategory(@PathVariable Long branchId, @PathVariable Long categoryId, @RequestBody MenuItemDto menuItemDto) {
        restaurantBranchService.addMenuItemToCategory(branchId, categoryId, menuItemDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/{branchId}/delete-menu-item/{categoryId}/{itemId}")
    public ResponseEntity<HttpStatus> deleteMenuItemFromCategory(@PathVariable("branchId") Long branchId,
                                                                 @PathVariable("categoryId") Long categoryId,
                                                                 @PathVariable("itemId") Long itemId) {
        restaurantBranchService.deleteMenuItemFromCategory(branchId, categoryId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
