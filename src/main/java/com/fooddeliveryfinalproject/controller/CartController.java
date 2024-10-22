package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.service.CartService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public CartDto createCart(Authentication authentication) {
        Cart cart = new Cart();
        Customer customer = (Customer) authentication.getPrincipal();
        return cartConverter.convertToModel(cartService.createOrderCart(cart, customer), new CartDto());
    }

    @GetMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CUSTOMER')")
    public CartDto getCartById(@PathVariable Long cartId, Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();

        return cartConverter.convertToModel(cartService.getOrderCartById(cartId, customer.getId()), new CartDto());
    }

    @PostMapping("/{cartId}/menuItem/{menuItemId}/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String addItemToCart(@PathVariable @Min(1) Long cartId,
                                @PathVariable @Min(1) Long menuItemId,
                                Authentication authentication,
                                @RequestParam(required = false) @Min(1)
                                    @DefaultValue("1") Integer qty
    ) {
        Customer customer = (Customer) authentication.getPrincipal();

        return cartService.addItemToCart(cartId, menuItemId, customer.getId(), qty);

    }

    @DeleteMapping("/{cartId}/menuItem/{menuItemId}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String removeItemFromCart(@PathVariable @Min(1) Long cartId,
                                     @PathVariable @Min(1) Long menuItemId,
                                     Authentication authentication
    ) {
        Customer customer = (Customer) authentication.getPrincipal();
        return cartService.removeItemFromCart(cartId, menuItemId, customer.getId());
    }

    @DeleteMapping("/{cartId}/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CUSTOMER')")
    public String deleteCart(@PathVariable @Min(1) Long cartId, Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();
        return cartService.deleteOrderCart(cartId, customer.getId());
    }
}
