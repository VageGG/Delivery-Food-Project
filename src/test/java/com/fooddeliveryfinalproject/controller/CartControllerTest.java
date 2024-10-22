package com.fooddeliveryfinalproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fooddeliveryfinalproject.config.JWTRequestFilter;
import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.converter.CustomerConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.model.CartItemDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.service.BlacklistService;
import com.fooddeliveryfinalproject.service.CartService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(CartController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class CartControllerTest {

    @MockBean
    private CartService cartService;

    @MockBean
    private CartConverter cartConverter;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTUtilService jwtUtilService;

    @MockBean
    private BlacklistService blacklistService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerRepo customerRepo;

    private Customer customer;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JWTRequestFilter jwtRequestFilter;

    @BeforeEach
    void setUp() throws Exception {
        customer = new Customer();
        customer.setId(1L);
        customer.setUsername("someone");
        customer.setEmail("someone@example.com");
        customer.setPassword("Password-13");
        customer.setPhoneNumber("1234567890");
        customer.setRole(User.Role.CUSTOMER);
    }

    @Test
    void createCart() throws Exception {
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setCustomer(customer);

        CartDto cartDto = new CartDto();
        cartDto.setCartId(1L);

        when(cartConverter.convertToModel(
                    cartService.createOrderCart(cart, new Customer()),
                    new CartDto()
                )
        ).thenReturn(cartDto);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        ResultActions response = mockMvc.perform(post("/cart/")
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("CUSTOMER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addItemToCart() throws Exception {
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setItems(new ArrayList<>());
        cart.setCustomer(customer);
        cart.getCustomer().setId(1L);

        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(1L);

        cart.getItems().add(new CartItem(cart, menuItem));

        CartDto cartDto = new CartDto();
        cartDto.setCartId(1L);

        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setMenuItemId(1L);

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartId(1L);
        cartItemDto.setMenuItemId(1L);

        when(cartService.addItemToCart(1L, 1L, 1L, 1)).thenReturn("item has been added to cart");

        ResultActions response = mockMvc.perform(post("/cart/1/menuItem/1/add")
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("CUSTOMER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto))
                .secure(false)
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void removeItemFromCart() throws Exception {
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setItems(new ArrayList<>());

        MenuItem menuItem = new MenuItem();
        menuItem.setMenuItemId(1L);

        CartDto cartDto = new CartDto();
        cartDto.setCartId(1L);

        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setMenuItemId(1L);

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCartId(1L);
        cartItemDto.setMenuItemId(1L);

        when(cartService.removeItemFromCart(1L, 1L, 1L)).thenReturn("item has been removed");

        ResultActions response = mockMvc.perform(delete("/cart/1/menuItem/1/remove")
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("CUSTOMER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteCart() throws Exception {
        Cart cart = new Cart();
        cart.setCartId(1L);

        when(cartService.getOrderCartById(1L, 1L)).thenReturn(cart);
        when(cartService.deleteOrderCart(1L, 1L)).thenReturn("cart has been deleted");

        ResultActions response = mockMvc.perform(delete("/cart/1/delete")
                .with(jwt())
                .with(SecurityMockMvcRequestPostProcessors.user("John")
                        .password("Password123+").roles("CUSTOMER"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("cart has been deleted")
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}