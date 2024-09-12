package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Cart;
import com.fooddeliveryfinalproject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByCustomerId(long customer);
}
