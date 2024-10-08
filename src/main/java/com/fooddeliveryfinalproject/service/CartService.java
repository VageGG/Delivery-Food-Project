package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CartConverter;
import com.fooddeliveryfinalproject.converter.MenuItemConverter;
import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.model.CartDto;
import com.fooddeliveryfinalproject.repository.CartItemRepo;
import com.fooddeliveryfinalproject.repository.CartRepo;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.MenuItemRepo;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepo repo;

    private final MenuItemService menuItemService;

    private final CartConverter cartConverter;

    private final MenuItemConverter menuItemConverter;

    private final CustomerRepo customerRepo;

    private final CartItemRepo cartItemRepo;

    private final EntityManager entityManager;

    private final MenuItemRepo menuItemRepo;

    @Autowired
    public CartService(CartRepo repo,
                       MenuItemService menuItemService,
                       CartConverter cartConverter,
                       MenuItemConverter menuItemConverter,
                       CustomerRepo customerRepo,
                       CartItemRepo cartItemRepo,
                       EntityManager entityManager,
                       MenuItemRepo menuItemRepo) {
        this.repo = repo;
        this.menuItemService = menuItemService;
        this.cartConverter = cartConverter;
        this.menuItemConverter = menuItemConverter;
        this.customerRepo = customerRepo;
        this.cartItemRepo = cartItemRepo;
        this.entityManager = entityManager;
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
    public String addItemToCart(Long cartId, Long menuItemId) {
        Cart cart = getOrderCartById(cartId);

        MenuItem menuItem = menuItemRepo.findById(menuItemId)
                .orElseThrow(() -> new NullPointerException("item not found"));

        cartItemRepo.save(new CartItem(cart, menuItem));

        return "item has been added to cart";
    }

    @Transactional
    public String removeItemFromCart(Long cartId, Long menuItemId) {
        Cart cart = repo.findById(cartId)
                .orElseThrow(() -> new NullPointerException("cart not found"));


        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("cart is empty");
        }

        if(!menuItemRepo.existsById(menuItemId)) {
            throw new NullPointerException("menu item not found");
        }

        CartItem cartItem = cartItemRepo.findByMenuItemId(menuItemId);

        if (cartItem.getCart().getCartId() != cartId) {
            throw new NullPointerException("item not in cart");
        }

        cartItemRepo.delete(cartItem);

        return "item has been removed";
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
