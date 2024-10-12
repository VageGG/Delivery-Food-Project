package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.repository.CartItemRepo;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private final CartRepo repo;

    private final CartConverter cartConverter;

    private final CustomerRepo customerRepo;

    private final CartItemRepo cartItemRepo;

    private final MenuItemRepo menuItemRepo;

    @Autowired
    public CartService(CartRepo repo,
                       CartConverter cartConverter,
                       CustomerRepo customerRepo,
                       CartItemRepo cartItemRepo,
                       MenuItemRepo menuItemRepo) {
        this.repo = repo;
        this.cartConverter = cartConverter;
        this.customerRepo = customerRepo;
        this.cartItemRepo = cartItemRepo;
        this.menuItemRepo = menuItemRepo;
    }

    @Transactional
    public Cart createOrderCart(Cart cart, long customerId) {
        if (cart == null) {
            throw new NullPointerException("cart is null");
        }

        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new NullPointerException("customer not found"));

        cart.setCustomer(customer);

        return this.repo.save(cart);
    }

    @Transactional
    public String addItemToCart(Long cartId, Long menuItemId, Long customerId) {
        Cart cart = getOrderCartById(cartId);

        if (cart.getCustomer().getId() != customerId) {
            throw new NullPointerException("cart not found");
        }

        MenuItem menuItem = menuItemRepo.findById(menuItemId)
                .orElseThrow(() -> new NullPointerException("item not found"));

        cartItemRepo.save(new CartItem(cart, menuItem));

        return "item has been added to cart";
    }

    @Transactional
    public String removeItemFromCart(Long cartId, Long menuItemId, Long customerId) {
        Cart cart = repo.findById(cartId)
                .orElseThrow(() -> new NullPointerException("cart not found"));

        if (cart.getCustomer().getId() != customerId) {
            throw new NullPointerException("cart not found");
        }


        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("cart is empty");
        }

        CartItem cartItem = cartItemRepo.findById(new CartItemId(cartId, menuItemId))
                .orElseThrow(() -> new NullPointerException("item is not in cart"));

        cartItemRepo.delete(cartItem);

        return "item has been removed";
    }

    @Transactional(readOnly = true)
    public Cart getOrderCartById(long id) {
        Cart cart = this.repo.findById(id)
                .orElseThrow(() -> new NullPointerException("cart not found"));

        cart.setItems(cartItemRepo.findByCartId(id));

        return cart;
    }

    @Transactional
    public String deleteOrderCart(Long cartId, Long customerId) {
        Cart cart = getOrderCartById(cartId);

        if (cart.getCustomer().getId() != customerId) {
            throw new NullPointerException("cart not found");
        }

        List<CartItem> cartItems = cartItemRepo.findAll().stream()
                .filter(cartItem -> cartItem.getCart().equals(cart))
                .toList();

        for (CartItem cartItem: cartItems) {
            cartItemRepo.delete(cartItem);
        }


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
