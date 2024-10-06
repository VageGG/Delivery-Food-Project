package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByRestaurant_RestId(Long restaurantId);

    List<Review> findAllByRestaurant_RestId(Long restaurantId);
}
