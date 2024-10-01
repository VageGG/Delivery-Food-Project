package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.entity.CartItem;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.MenuItem;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.model.MenuItemDto;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrderCart() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);

        when(cartRepo.save(cart)).thenReturn(cart);

        // when
        Cart savedCart = cartService.createOrderCart(cart);

        // then
        assertEquals(cart.getCartId(), savedCart.getCartId());
    }

    @Test
    void createOrderCartShouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> cartService.createOrderCart(null));
    }

    @Test
    void addItemToCart() {
        // given
        Long cartId = 1L;
        Long itemId = 1L;

        Cart cart = new Cart();
        cart.setCartId(cartId);
        cart.setItems(new ArrayList<>());

        MenuItem item = new MenuItem();
        item.setMenuItemId(itemId);

        when(cartRepo.findById(cartId)).thenReturn(Optional.of(cart));
        when(menuItemConverter.convertToEntity(
                    menuItemService.getMenuItemById(itemId),
                    new MenuItem()
                )
        ).thenReturn(item);

        cart.getItems().add(new CartItem(cart, item));

        when(cartRepo.save(cart)).thenReturn(cart);

        // when
        Cart savedCart = cartService.addItemToCart(cart.getCartId(), item.getMenuItemId());

        // then
        assertEquals(cart.getItems().size(), savedCart.getItems().size());

    }

    @Test
    void removeItemFromCart() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);
        cart.setItems(new ArrayList<>());

        cart.getItems().add(new CartItem());

        MenuItem item = new MenuItem();
        item.setMenuItemId(1L);

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));
        when(menuItemConverter.convertToEntity(
                menuItemService.getMenuItemById(1L),
                new MenuItem())
        ).thenReturn(item);

        when(cartRepo.save(cart)).thenReturn(cart);

        // when
        Cart savedCart = cartService.removeItemFromCart(1L, 1L);

        // then
        assertEquals(cart.getItems().size(), savedCart.getItems().size());
    }

    @Test
    void removeItemFromCartShouldReturnCartIsEmpty() {
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));

        assertThrows(
                RuntimeException.class,
                () -> cartService.removeItemFromCart(1L, 1L)
        );
    }

    @Test
    void getOrderCartById() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));

        // when
        Cart savedCart = cartService.getOrderCartById(1L);

        // then
        assertEquals(cart.getCartId(), savedCart.getCartId());
    }

    @Test
    void getOrderCartByIdShouldThrowNullPointerException() {
        when(cartRepo.findById(1L)).thenReturn(null);
        assertThrows(NullPointerException.class, () -> cartService.getOrderCartById(1L));
    }

    @Test
    void deleteOrderCart() {
        // given
        Cart cart = new Cart();
        cart.setCartId(1L);

        when(cartRepo.findById(1L)).thenReturn(Optional.of(cart));

        // when
        String message = cartService.deleteOrderCart(1L);

        // then
        assertEquals("cart has been deleted", message);
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