package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantBranchRepo extends JpaRepository<RestaurantBranch,Long> {
}
