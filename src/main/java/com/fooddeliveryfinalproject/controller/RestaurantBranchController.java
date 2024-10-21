package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.service.MenuService;
import com.fooddeliveryfinalproject.service.RestaurantBranchService;
import com.fooddeliveryfinalproject.service.RestaurantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantBranchController(MenuService menuService,
                                      RestaurantBranchService restaurantBranchService,
                                      RestaurantService restaurantService) {
        this.menuService = menuService;
        this.restaurantBranchService = restaurantBranchService;
        this.restaurantService = restaurantService;
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
    public ResponseEntity<HttpStatus> createRestaurantBranch(@PathVariable("restId") @Min(1) Long id,
                                                             @RequestBody @Valid RestaurantBranchDto restaurantBranchDto,
                                                             Authentication authentication) {
        Restaurant restaurant = restaurantService.getRest(id);
        RestaurantManager restaurantManager = (RestaurantManager) authentication.getPrincipal();
        if (restaurant.getRestaurantManager().getId() == restaurantManager.getId()) {
            restaurantBranchService.createRestaurantBranch(id, restaurantBranchDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateRestaurantBranch(@PathVariable("id") @Min(1) Long id,
                                                             @RequestBody @Valid RestaurantBranchDto restaurantBranchDto,
                                                             Authentication authentication) {
        RestaurantBranch restaurantBranch = restaurantBranchService.getRestBranch(id);
        Restaurant restaurant = restaurantService.getRest(restaurantBranch.getRestaurant().getRestId());
        RestaurantManager restaurantManager = (RestaurantManager) authentication.getPrincipal();
        if (restaurant.getRestaurantManager().getId() == restaurantManager.getId()) {
            restaurantBranchService.updateRestaurantBranch(id, restaurantBranchDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteRestaurantBranch(@PathVariable("id") @Min(1) Long id,
                                                             Authentication authentication) {
        RestaurantBranch restaurantBranch = restaurantBranchService.getRestBranch(id);
        Restaurant restaurant = restaurantService.getRest(restaurantBranch.getRestaurant().getRestId());
        RestaurantManager restaurantManager = (RestaurantManager) authentication.getPrincipal();
        if (restaurant.getRestaurantManager().getId() == restaurantManager.getId()) {
            restaurantBranchService.deleteRestaurantBranch(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/categories/{branchId}")
    public List<MenuCategoryDto> getAllMenuCategoriesByBranchId(@PathVariable @Min(1) Long branchId) {
        return restaurantBranchService.getAllCategoriesByBranchId(branchId);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/add-category/{branchId}")
    public ResponseEntity<HttpStatus> addMenuCategoryToBranch(@PathVariable @Min(1) Long branchId,
                                                              @RequestBody MenuCategoryDto menuCategoryDto,
                                                              Authentication authentication) {
        RestaurantBranch restaurantBranch = restaurantBranchService.getRestBranch(branchId);
        Restaurant restaurant = restaurantService.getRest(restaurantBranch.getRestaurant().getRestId());
        RestaurantManager restaurantManager = (RestaurantManager) authentication.getPrincipal();
        if (restaurant.getRestaurantManager().getId() == restaurantManager.getId()) {
            restaurantBranchService.addMenuCategoryToBranch(branchId, menuCategoryDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/{branchId}/delete-category/{categoryId}")
    public ResponseEntity<HttpStatus> deleteMenuCategoryFromBranch(@PathVariable("branchId") @Min(1) Long branchId,
                                                                   @PathVariable("categoryId") @Min(1) Long categoryId,
                                                                   Authentication authentication) {
        RestaurantBranch restaurantBranch = restaurantBranchService.getRestBranch(branchId);
        Restaurant restaurant = restaurantService.getRest(restaurantBranch.getRestaurant().getRestId());
        RestaurantManager restaurantManager = (RestaurantManager) authentication.getPrincipal();
        if (restaurant.getRestaurantManager().getId() == restaurantManager.getId()) {
            restaurantBranchService.deleteMenuCategoryFromBranch(branchId, categoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @PostMapping("/{branchId}/add-menu-item/{categoryId}")
    public ResponseEntity<HttpStatus> addMenuItemToCategory(@PathVariable @Min(1) Long branchId,
                                                            @PathVariable @Min(1) Long categoryId,
                                                            @RequestBody @Valid MenuItemDto menuItemDto,
                                                            Authentication authentication) {
        RestaurantBranch restaurantBranch = restaurantBranchService.getRestBranch(branchId);
        Restaurant restaurant = restaurantService.getRest(restaurantBranch.getRestaurant().getRestId());
        RestaurantManager restaurantManager = (RestaurantManager) authentication.getPrincipal();
        if (restaurant.getRestaurantManager().getId() == restaurantManager.getId()) {
            restaurantBranchService.addMenuItemToCategory(branchId, categoryId, menuItemDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('RESTAURANT_MANAGER')")
    @DeleteMapping("/{branchId}/delete-menu-item/{categoryId}/{itemId}")
    public ResponseEntity<HttpStatus> deleteMenuItemFromCategory(@PathVariable("branchId") @Min(1) Long branchId,
                                                                 @PathVariable("categoryId") @Min(1) Long categoryId,
                                                                 @PathVariable("itemId") @Min(1) Long itemId,
                                                                 Authentication authentication) {
        RestaurantBranch restaurantBranch = restaurantBranchService.getRestBranch(branchId);
        Restaurant restaurant = restaurantService.getRest(restaurantBranch.getRestaurant().getRestId());
        RestaurantManager restaurantManager = (RestaurantManager) authentication.getPrincipal();
        if (restaurant.getRestaurantManager().getId() == restaurantManager.getId()) {
            restaurantBranchService.deleteMenuItemFromCategory(branchId, categoryId, itemId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/categories-with-menu-items/{branchId}")
    public ResponseEntity<List<MenuCategoryWithItemsDto>> getCategoriesWithItemsByBranchId(@PathVariable @Min(1) Long branchId) {
        List<MenuCategoryWithItemsDto> categoriesWithItems = restaurantBranchService.getCategoriesWithItemsByBranchId(branchId);
        return ResponseEntity.ok(categoriesWithItems);
    }

    @GetMapping("/{branchId}/category/{categoryId}")
    public ResponseEntity<MenuCategoryWithItemsDto> getCategoryWithItemsByBranchIdAndCategoryId(@PathVariable @Min(1) Long branchId,
                                                                                                @PathVariable @Min(1) Long categoryId) {
        MenuCategoryWithItemsDto categoryWithItems = restaurantBranchService.getCategoryWithItemsByBranchIdAndCategoryId(branchId, categoryId);
        return ResponseEntity.ok(categoryWithItems);
    }


}
