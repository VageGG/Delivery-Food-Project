package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.config.DeliveryFoodException;
import com.fooddeliveryfinalproject.converter.MenuConverter;
import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import com.fooddeliveryfinalproject.model.MenuDto;
import com.fooddeliveryfinalproject.repository.MenuRepo;
import com.fooddeliveryfinalproject.repository.RestaurantBranchRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {


    private final MenuRepo menuRepo;

    private final MenuConverter menuConverter;

  //  private final RestaurantBranchRepo restaurantBranchRepo;



    public MenuService(MenuRepo menuRepo,
                       MenuConverter menuConverter
     //                  RestaurantBranchRepo restaurantBranchRepo
    ){

        this.menuRepo = menuRepo;
        this.menuConverter = menuConverter;
   //     this.restaurantBranchRepo= restaurantBranchRepo;
    }

//    @Transactional
//    public MenuDto createMenu(MenuDto menuDto) {
//        Menu menu = menuConverter.convertToEntity(menuDto, new Menu());
//        return menuConverter.convertToModel(this.menuRepo.save(menu), new MenuDto());
//    }

    public MenuDto getMenuById(Long id) {
        return menuConverter.convertToModel(
                        menuRepo.findById(id).orElseThrow(() ->
                                new DeliveryFoodException("menu not found")), new MenuDto());
    }


    public List<MenuDto> getAllMenus() {
        return this.menuRepo.findAll().stream()
                .map(menus -> menuConverter.convertToModel(menus, new MenuDto()))
                .collect(Collectors.toList());
    }

//    @Transactional
//    public MenuDto updateMenu(MenuDto menuDto) {
//        getMenuById(menuDto.getId());
//        Menu menu = menuConverter.convertToEntity(menuDto, new Menu());
//        return menuConverter.convertToModel(this.menuRepo.save(menu), new MenuDto());
//    }

    @Transactional
    public void deleteMenu(long id) {
        menuRepo.deleteById(id);
    }

    public MenuDto getMenuByRestaurantBranchId(Long restaurantBranchId) {
        Menu menu = menuRepo.findByRestaurantBranchId(restaurantBranchId);
        return menuConverter.convertToModel(menu, new MenuDto());
    }

//    public Menu createMenu(Long restaurantBranchId, MenuDto menuDto) {
//        RestaurantBranch restaurantBranch = restaurantBranchRepo.findById(restaurantBranchId)
//                .orElseThrow(() -> new DeliveryFoodException("RestaurantBranch not found"));
//
//        Menu menu = menuConverter.convertToEntity(menuDto,new Menu());
//        menu.setRestaurantBranch(restaurantBranch);
//        return menuRepo.save(menu);
//    }

//    public void deleteMenuByRestaurantBranchId(Long restaurantBranchId) {
//        menuRepo.deleteByRestaurantBranchId(restaurantBranchId);
//    }

//    public Menu updateMenu(Long restaurantBranchId, MenuDto menuDto) {
//        Menu existingMenu = menuRepo.findByRestaurantBranchId(restaurantBranchId);
//
//        if (existingMenu == null) {
//            throw new DeliveryFoodException("Menu not found for RestaurantBranch with ID " + restaurantBranchId);
//        }
//
//        existingMenu.setMenuItems(menuDto.getMenuItems());
//        return menuRepository.save(existingMenu);
//    }

}
