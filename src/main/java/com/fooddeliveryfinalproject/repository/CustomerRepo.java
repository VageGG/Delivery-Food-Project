package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);

}
