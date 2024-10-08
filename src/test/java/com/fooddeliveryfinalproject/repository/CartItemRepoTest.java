package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.*;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class CartItemRepoTest {
    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private MenuCategoryRepo menuCategoryRepo;

    @Autowired
    private MenuRepo menuRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Autowired
    private RestaurantBranchRepo restaurantBranchRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private RestaurantManagerRepo restaurantManagerRepo;

    @Test
    void findByMenuItemId() {
//        Customer customer = new Customer();
//        customer.setId(1L);
//        customer.setUsername("someone");
//        customer.setEmail("someone@gmail.com");
//        customer.setRole(User.Role.CUSTOMER);
//        customer.setPhoneNumber("43637882");
//        customer.setPassword("Password123+");
//        Customer savedCustomer = customerRepo.save(customer);
//
//        RestaurantManager restaurantManager = new RestaurantManager();
//        restaurantManager.setStatus(RegistrationStatus.APPROVED);
//        restaurantManager.setUsername("KFCManger");
//        restaurantManager.setRole(User.Role.RESTAURANT_MANAGER);
//        restaurantManager.setEmail("kfcManager@gmail.com");
//        restaurantManager.setPhoneNumber("947873");
//        RestaurantManager savedRestaurantManager = restaurantManagerRepo.save(restaurantManager);
//
//        Restaurant restaurant = new Restaurant();
//        restaurant.setName("KFC");
//        restaurant.setRestaurantManager(savedRestaurantManager);
//        Restaurant savedRestaurant = restaurantRepo.save(restaurant);
//
//        RestaurantBranch restaurantBranch = new RestaurantBranch();
//        restaurantBranch.setRestaurant(savedRestaurant);
//        restaurantBranch.setPhoneNumber("123456");
//        RestaurantBranch savedRestaurantBranch = restaurantBranchRepo.save(restaurantBranch);
//
//        Menu menu = new Menu();
//        menu.setMenuCategories(new ArrayList<>());
//        menu.setRestaurantBranch(savedRestaurantBranch);
//        Menu savedMenu = menuRepo.save(menu);
//
//        MenuCategory menuCategory = new MenuCategory();
//        menuCategory.setName("burgers");
//        menuCategory.setItems(new ArrayList<>());
//        menuCategory.setMenu(savedMenu);
//        MenuCategory savedMenuCategory = menuCategoryRepo.save(menuCategory);

        MenuItem menuItem = new MenuItem();
        menuItem.setName("burger");
        menuItem.setPrice(1.0);
        menuItem.setDescription("some description");
        menuItem.setMenuCategory(new MenuCategory());
        menuItem.getMenuCategory().setCategoryId(1L);
        MenuItem savedMenuItem = menuItemRepo.save(menuItem);

        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setCustomer(new Customer());
        Cart savedCart = cartRepo.save(cart);

        CartItem cartItem = new CartItem();
        cartItem.setMenuItem(savedMenuItem);
        cartItem.setCart(savedCart);
        CartItem savedCartItem = cartItemRepo.save(cartItem);

        CartItem response = cartItemRepo.findByMenuItemId(savedMenuItem.getMenuItemId());

        assertNotNull(response);
        assertEquals(savedCartItem, response);
    }

    @Test
    void findByCartId() {
    }
}