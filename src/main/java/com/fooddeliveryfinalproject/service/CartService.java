package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.repository.CartRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepo repo;

    private final MenuItemService menuItemService;

    private final CartConverter cartConverter;

    private final MenuItemConverter menuItemConverter;

    @Autowired
    public CartService(CartRepo repo,
                       MenuItemService menuItemService,
                       CartConverter cartConverter,
                       MenuItemConverter menuItemConverter) {
        this.repo = repo;
        this.menuItemService = menuItemService;
        this.cartConverter = cartConverter;
        this.menuItemConverter = menuItemConverter;
    }

    @Transactional
    public Cart createOrderCart(Cart cart) {
        if (cart == null) {
            throw new NullPointerException("cart is null");
        }

        return this.repo.save(cart);
    }

    @Transactional
    public Cart addItemToCart(Long cartId, Long menuItemId) {
        Cart cart = getOrderCartById(cartId);

        MenuItem menuItem =
                menuItemConverter.convertToEntity (
                    menuItemService.getMenuItemById(menuItemId),
                    new MenuItem()
                );

        cart.getItems().add(new CartItem(cart, menuItem));

        return repo.save(cart);
    }

    @Transactional
    public Cart removeItemFromCart(Long cartId, Long menuItemId) {
        Cart cart = getOrderCartById(cartId);

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("cart is empty");
        }

        MenuItem menuItem =
                menuItemConverter.convertToEntity (
                        menuItemService.getMenuItemById(menuItemId),
                        new MenuItem()
                );

        CartItem cartItem;
        for (int i = 0; i < cart.getItems().size(); i++) {
            cartItem = cart.getItems().get(i);

            if (cartItem.getMenuItem() == menuItem) {
                cart.getItems().remove(cartItem);
            }
        }

        return repo.save(cart);
    }

    @Transactional(readOnly = true)
    public Cart getOrderCartById(long id) {
        return this.repo.findById(id)
                .orElseThrow(() -> new NullPointerException("cart not found"));
    }

    @Transactional
    public String deleteOrderCart(long id) {
        Cart cart = getOrderCartById(id);
        this.repo.delete(cart);
        return "cart has been deleted";
    }

    @Transactional(readOnly = true)
    public CartDto getCartByCustomerId(Long customerId) {
        Cart cart = this.repo.findByCustomerId(customerId);

        if (cart == null) {
            throw new RuntimeException("cart not found");
        }

        return cartConverter.convertToModel(cart, new CartDto());
    }
}
