package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.CartItem;
import com.fooddeliveryfinalproject.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, CartItemId> {
    CartItem findByMenuItemId(Long menuItemId);
    CartItem findByCartId(Long menuItemId);
}
