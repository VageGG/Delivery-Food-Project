package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.RestaurantBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepo extends JpaRepository<Menu,Long> {
    Menu findByRestaurantBranch_RestBranchId(Long restaurantBranchId);

}
