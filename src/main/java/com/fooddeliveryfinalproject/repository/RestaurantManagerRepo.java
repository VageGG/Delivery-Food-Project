package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.RestaurantManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantManagerRepo extends JpaRepository<RestaurantManager, Long> {
}
