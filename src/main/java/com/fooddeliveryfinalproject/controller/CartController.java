package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.service.CartService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;


    private final CartConverter cartConverter;

    @Autowired
    public CartController(CartService cartService, CartConverter cartConverter) {
        this.cartService = cartService;
        this.cartConverter = cartConverter;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartDto createCart(@RequestParam @Min(1) long customerId) {
        Cart cart = new Cart();
        return cartConverter.convertToModel(cartService.createOrderCart(cart, customerId), new CartDto());
    }

    @GetMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartDto getCartById(@PathVariable Long cartId) {
        return cartConverter.convertToModel(cartService.getOrderCartById(cartId), new CartDto());
    }

    @PostMapping("/{cartId}/menuItem/{menuItemId}/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String addItemToCart(@PathVariable @Min(1) Long cartId,
                                @PathVariable @Min(1) Long menuItemId,
                                @RequestParam @Min(1) Long customerId
    ) {
        return cartService.addItemToCart(cartId, menuItemId, customerId);

    }

    @DeleteMapping("/{cartId}/menuItem/{menuItemId}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String removeItemFromCart(@PathVariable @Min(1) Long cartId,
                                     @PathVariable @Min(1) Long menuItemId,
                                     @RequestParam @Min(1) Long customerId
    ) {
        return cartService.removeItemFromCart(cartId, menuItemId, customerId);
    }

    @DeleteMapping("/{cartId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String deleteCart(@PathVariable @Min(1) Long cartId, @RequestParam @Min(1) Long customerId) {
        return cartService.deleteOrderCart(cartId, customerId);
    }
}
