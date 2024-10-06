package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.*;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.*;
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
import java.util.stream.Collectors;

@Service
public class RestaurantBranchService {
    private final RestaurantBranchRepo restaurantBranchRepo;

    private final RestaurantBranchConverter restaurantBranchConverter;

    private final MenuRepo menuRepo;

    private final MenuCategoryRepo menuCategoryRepo;

    private final MenuCategoryConverter menuCategoryConverter;

    private final MenuItemRepo menuItemRepo;

    private final MenuItemConverter menuItemConverter;

    private final AddressService addressService;

    private final RestaurantService restaurantService;

    private final RestaurantConverter restaurantConverter;

    private final AddressConverter addressConverter;


    @Autowired
    public RestaurantBranchService(RestaurantBranchRepo restaurantBranchRepo,
                                   RestaurantBranchConverter restaurantBranchConverter,
                                   MenuRepo menuRepo,
                                   MenuCategoryRepo menuCategoryRepo,
                                   MenuCategoryConverter menuCategoryConverter,
                                   MenuItemRepo menuItemRepo,
                                   MenuItemConverter menuItemConverter,
                                   AddressService addressService,
                                   RestaurantService restaurantService,
                                   RestaurantConverter restaurantConverter,
                                   AddressConverter addressConverter) {
        this.restaurantBranchRepo = restaurantBranchRepo;
        this.restaurantBranchConverter = restaurantBranchConverter;
        this.menuRepo = menuRepo;
        this.menuCategoryRepo = menuCategoryRepo;
        this.menuCategoryConverter = menuCategoryConverter;
        this.menuItemRepo = menuItemRepo;
        this.menuItemConverter = menuItemConverter;
        this.addressService = addressService;
        this.restaurantService = restaurantService;
        this.restaurantConverter = restaurantConverter;
        this.addressConverter = addressConverter;
    }

    @Transactional(readOnly = true)
    public RestaurantBranch getRestaurantBranch(Long id) {
        return restaurantBranchRepo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Could not find RestaurantBranch"));
    }

    @Transactional(readOnly = true)
    public Page<RestaurantBranchDto> getAllRestaurantBranches(Long restaurantId, Pageable pageable) {
        if (restaurantId == null) {
            throw new RuntimeException("Restaurant ID is required");
        }

        Page<RestaurantBranch> branches = restaurantBranchRepo.findAllByRestaurant_RestId(restaurantId, pageable);
        return branches.map(restaurantBranch -> restaurantBranchConverter.convertToModel(restaurantBranch, new RestaurantBranchDto()));
    }


    @Transactional
    public void createRestaurantBranch(Long restId, RestaurantBranchDto restaurantBranchDto) {
        RestaurantDto restaurant = restaurantService.getRestaurantById(restId);
        RestaurantBranch restaurantBranch = restaurantBranchConverter.convertToEntity(restaurantBranchDto, new RestaurantBranch());
        restaurantBranch.setRestaurant(restaurantConverter.convertToEntity(restaurant, new Restaurant()));

        AddressDto addressDto = restaurantBranchDto.getAddressDto();
        Address address = addressService.createAddress(addressDto);
        restaurantBranch.setAddress(address);

        restaurantBranchRepo.save(restaurantBranch);
        Menu menu = new Menu();
        menu.setRestaurantBranch(restaurantBranch);
        restaurantBranch.setMenu(menu);
        menuRepo.save(menu);
        restaurantBranchRepo.save(restaurantBranch);
    }

    @Transactional
    public void updateRestaurantBranch(Long id, RestaurantBranchDto restaurantBranchDto) {
        RestaurantBranch restaurantBranch = restaurantBranchRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find RestaurantBranch"));

        restaurantBranch.setPhoneNumber(restaurantBranchDto.getPhoneNumber());

        AddressDto addressDto = restaurantBranchDto.getAddressDto();
        if (addressDto != null) {
            Long addressId = restaurantBranch.getAddress().getId();
            if (addressId != null) {
                addressService.updateAddress(addressId, addressDto);
            } else {
                Address newAddress = addressConverter.convertToEntity(addressDto, new Address());
                Address savedAddress = addressService.createAddress(addressConverter.convertToModel(newAddress, new AddressDto()));
                restaurantBranch.setAddress(savedAddress);
            }
        }

        MenuDto menuDto = restaurantBranchDto.getMenuDto();
        if (menuDto != null) {
            Menu menu = menuRepo.findById(menuDto.getId())
                    .orElseThrow(() -> new RuntimeException("Could not find Menu"));
            restaurantBranch.setMenu(menu);
            menu.setRestaurantBranch(restaurantBranch);
            menuRepo.save(menu);
        }

        restaurantBranchRepo.save(restaurantBranch);
    }

    @Transactional
    public void deleteRestaurantBranch(Long id) {
        restaurantBranchRepo.deleteById(id);
    }

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

    @Transactional
    public void addMenuCategoryToBranch(Long branchId, MenuCategoryDto menuCategoryDto) {

        Menu menu = menuRepo.findByRestaurantBranch_RestBranchId(branchId);

        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }

        MenuCategory menuCategory =  new MenuCategory();
        menuCategory.setName(menuCategoryDto.getName());
        menuCategory.setMenu(menu);
        menuCategoryRepo.save(menuCategory);
    }


    @Transactional
    public void deleteMenuCategoryFromBranch(Long branchId, Long categoryId) {

        Menu menu = menuRepo.findByRestaurantBranch_RestBranchId(branchId);

        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }

        MenuCategory menuCategory = menuCategoryRepo.findByCategoryIdAndMenu(categoryId, menu)
                .orElseThrow(() -> new RuntimeException("Menu Category with ID " + categoryId + " not found"));

        menuCategoryRepo.delete(menuCategory);
    }

    @Transactional
    public void addMenuItemToCategory(Long branchId, Long categoryId, MenuItemDto menuItemDto) {

        Menu menu = menuRepo.findByRestaurantBranch_RestBranchId(branchId);
        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }
        MenuCategory menuCategory = menuCategoryRepo.findByCategoryIdAndMenu(categoryId, menu)
                .orElseThrow(() -> new RuntimeException("Menu Category with ID " + categoryId + " not found"));

        MenuItem menuItem = menuItemConverter.convertToEntity(menuItemDto, new MenuItem());

        menuItem.setMenuCategory(menuCategory);

        menuItemRepo.save(menuItem);
    }

    @Transactional
    public void deleteMenuItemFromCategory(Long branchId, Long categoryId, Long itemId) {

        Menu menu = menuRepo.findByRestaurantBranch_RestBranchId(branchId);
        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }

        MenuCategory menuCategory = menuCategoryRepo.findByCategoryIdAndMenu(categoryId, menu)
                .orElseThrow(() -> new RuntimeException("Menu Category with ID " + categoryId + " not found"));

        MenuItem menuItem = menuItemRepo.findByMenuItemIdAndMenuCategory(itemId, menuCategory)
                .orElseThrow(() -> new RuntimeException("Menu Item with ID " + itemId + " not found"));

        menuItemRepo.delete(menuItem);
    }

    public List<MenuCategoryWithItemsDto> getCategoriesWithItemsByBranchId(Long branchId) {

        RestaurantBranch branch = restaurantBranchRepo.findById(branchId)
                .orElseThrow(() -> new RuntimeException("Branch with ID " + branchId + " not found"));

        Menu menu = menuRepo.findByRestaurantBranch_RestBranchId(branchId);

        if (menu == null) {
            throw new RuntimeException("No menu found for branch with ID " + branchId);
        }

        List<MenuCategory> categories = menuCategoryRepo.findAllByMenu(menu);

        return categories.stream()
                .map(category -> {
                    MenuCategoryWithItemsDto menuCategoryWithItemsDto = new MenuCategoryWithItemsDto();

                    menuCategoryWithItemsDto.setCategoryId(category.getCategoryId());
                    menuCategoryWithItemsDto.setName(category.getName());

                    List<MenuItemDto> menuItems = category.getItems().stream()
                            .map(item -> {
                                MenuItemDto menuItemDto = new MenuItemDto();
                                return menuItemConverter.convertToModel(item, menuItemDto);
                            })
                            .collect(Collectors.toList());

                    menuCategoryWithItemsDto.setItems(menuItems);
                    return menuCategoryWithItemsDto;
                })
                .collect(Collectors.toList());
    }

}