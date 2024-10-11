package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.exceptions.DeliveryFoodException;
import com.fooddeliveryfinalproject.converter.MenuConverter;
import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.repository.MenuRepo;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class MenuService {

    private final MenuRepo menuRepo;

    private final MenuConverter menuConverter;

    @Autowired
    public MenuService(MenuRepo menuRepo,
                       MenuConverter menuConverter) {

        this.menuRepo = menuRepo;
        this.menuConverter = menuConverter;
    }

    @Transactional(readOnly = true)
    public MenuDto getMenuById(@Min(1) Long id) {
        return menuConverter.convertToModel(
                        menuRepo.findById(id).orElseThrow(() ->
                                new DeliveryFoodException("menu not found")), new MenuDto());
    }


    @Transactional(readOnly = true)
    public List<MenuDto> getAllMenus() {
        return this.menuRepo.findAll().stream()
                .map(menus -> menuConverter.convertToModel(menus, new MenuDto()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteMenu(@Min(1) Long id) {
        menuRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public MenuDto getMenuByRestaurantBranchId(Long restaurantBranchId) {
        Menu menu = menuRepo.findByRestaurantBranch_RestBranchId(restaurantBranchId);
        return menuConverter.convertToModel(menu, new MenuDto());
    }
}
