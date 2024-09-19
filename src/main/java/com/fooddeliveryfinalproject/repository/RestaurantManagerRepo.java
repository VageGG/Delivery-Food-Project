package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.RestaurantManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantManagerRepo extends JpaRepository<RestaurantManager, Long> {
    Optional<RestaurantManager> findByUsername(String username);
}
