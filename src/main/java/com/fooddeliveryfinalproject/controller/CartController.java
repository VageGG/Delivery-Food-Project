package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartConverter cartConverter;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartDto createCart() {
        Cart cart = new Cart();

        return cartConverter.convertToModel(cartService.createOrderCart(cart), new CartDto());
    }

    @PostMapping("/{cartId}/menuItem/{menuItemId}/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartDto addItemToCart(@PathVariable Long cartId, @PathVariable Long menuItemId) {
        return cartConverter.convertToModel (
                cartService.addItemToCart(cartId, menuItemId),
                new CartDto()
        );
    }

    @DeleteMapping("/{cartId}/menuItem/{menuItemId}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartDto removeItemFromCart(@PathVariable Long cartId, @PathVariable Long menuItemId) {
        return cartConverter.convertToModel (
                cartService.removeItemFromCart(cartId, menuItemId),
                new CartDto()
        );
    }

    @DeleteMapping("/{cartId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String deleteCart(@PathVariable Long cartId) {
        return cartService.deleteOrderCart(cartId);
    }
}
