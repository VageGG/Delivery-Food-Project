package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Restaurant;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface RestaurantBranchRepo extends JpaRepository<RestaurantBranch, Long> {

    Page<RestaurantBranch> findAllByRestaurant_RestId(Long restaurantId, Pageable pageable);
}
