package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.*;
import com.fooddeliveryfinalproject.repository.CartRepo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartRepo repo;
    @Autowired
    private MenuItemService menuItemService;

    @Transactional
    public Cart createOrderCart(Cart orderCart) {
        return this.repo.save(orderCart);
    }

    public Cart getOrderCartById(long id) {
        Cart orderCart = this.repo.findById(id).get();
        if (orderCart == null) {
            throw new RuntimeException("order cart not found");
        }

        return orderCart;
    }

    public Double calculateTotal(long customerId) {
        List<CartItem> items = this.repo.findByCustomerId(customerId).getItems();

        if (items.size() == 0) {
            throw new RuntimeException("cart is empty");
        }

        double total = 0;
        for (CartItem cartItem: items) {
            total+= total + this.menuItemService.getMenuItemById(cartItem.getMenuItemId()).getPrice();
        }

        return total;
    }

    @Transactional
    public Cart updateOrderCart(Cart orderCart) {
        getOrderCartById(orderCart.getCartId()); //checking if orderCart exists
        return this.repo.save(orderCart);
    }

    @Transactional
    public void deleteOrderCart(long id) {
        Cart orderCart = getOrderCartById(id);
        this.repo.delete(orderCart);
    }
}
