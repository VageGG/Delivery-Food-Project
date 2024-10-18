package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.repository.CartItemRepo;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepo cartRepo;

    @Mock
    private CartConverter cartConverter;

    @Mock
    private MenuItemRepo menuItemRepo;

    @Mock
    private MenuItemService menuItemService;

    @Mock
    private MenuItemConverter menuItemConverter;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private CartItemRepo cartItemRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderCart() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setCustomer(new Customer());
        cart.getCustomer().setId(1L);

        when(cartRepo.save(cart)).thenReturn(cart);

        // when
        when(customerRepo.findById(1L)).thenReturn(Optional.ofNullable(cart.getCustomer()));
        Cart savedCart = cartService.createOrderCart(cart, new Customer());

        // then
        assertEquals(cart.getCartId(), savedCart.getCartId());
    }

    @Test
    void createOrderCartShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.createOrderCart(null, new Customer()));
    }

    @Test
    void addItemToCart() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;

        Cart cart = new Cart();
        cart.setCartId(cartId);
        cart.setItems(new ArrayList<>());
        cart.setCustomer(new Customer());
        cart.getCustomer().setId(1L);

        MenuItem item = new MenuItem();
        item.setMenuItemId(1L);
        item.setMenuItemId(itemId);

        CartItem cartItem = new CartItem(cart, item);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(menuItemRepo.findById(1L)).thenReturn(Optional.of(item));
        when(cartItemRepo.save(new CartItem(cart, item))).thenReturn(cartItem);

        // when
        String response = cartService.addItemToCart(cart.getCartId(), item.getMenuItemId(), 1L);

        // then
        assertEquals(response, "item has been added to cart");

    }

    @Test
    void removeItemFromCart() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setItems(new ArrayList<>());
        cart.setCustomer(new Customer());
        cart.getCustomer().setId(1L);

        MenuItem item = new MenuItem();
        item.setMenuItemId(1L);

        CartItem cartItem = new CartItem(cart, item);
        cart.getItems().add(cartItem);

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));
        when(cartItemRepo.findById(new CartItemId(1L, 1L)))
                .thenReturn(Optional.of(cartItem));

        // when
        String response = cartService.removeItemFromCart(1L, 1L, 1L);

        // then
        assertEquals(response, "item has been removed");
    }

    @Test
    void removeItemFromCartShouldReturnCartIsEmpty() {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        cart.setCustomer(new Customer());
        cart.getCustomer().setId(2L);

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));

        assertThrows(
                RuntimeException.class,
                () -> cartService.removeItemFromCart(1L, 1L, 1L)
        );
    }

    @Test
    void getOrderCartById() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setCustomer(new Customer());
        cart.getCustomer().setId(1L);

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));

        // when
        Cart savedCart = cartService.getOrderCartById(1L, 1L);

        // then
        assertEquals(cart.getCartId(), savedCart.getCartId());
    }

    @Test
    void getOrderCartByIdShouldThrowNullPointerException() {
        when(cartRepo.findById(1L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> cartService.getOrderCartById(1L, 1L));
    }

    @Test
    void deleteOrderCart() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setCustomer(new Customer());
        cart.getCustomer().setId(1L);

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));

        // when
        String message = cartService.deleteOrderCart(1L, 1L);

        // then
        assertEquals("all cart items have been removed", message);
    }

    @Test
    void getCartByCustomerId() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setCustomer(new Customer());
        cart.getCustomer().setId(1L);

        CartDto cartDto = new CartDto();
        cartDto.setCartId(1L);
//        cartDto.setCustomerDto(new CustomerDto());
//        cartDto.getCustomerDto().setId(1L);

        when(cartRepo.findByCustomerId(1L)).thenReturn(cart);
        when(cartConverter.convertToModel(
                    any(Cart.class),
                    any(CartDto.class)
                )
        ).thenReturn(cartDto);

        // when
        CartDto responseDto = cartService.getCartByCustomerId(1L);

        // then
        assertEquals(responseDto.getCartId(), cartDto.getCartId());
    }

    @Test
    void getCartByCustomerIdShouldReturnCartNotFound() {
        when(cartRepo.findById(1L)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> cartService.getCartByCustomerId(1L));
    }
}