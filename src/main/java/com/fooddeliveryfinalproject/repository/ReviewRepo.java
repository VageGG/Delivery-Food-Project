package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
