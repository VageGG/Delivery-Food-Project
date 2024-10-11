package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepo extends JpaRepository<Menu,Long> {
    Menu findByRestaurantBranch_RestBranchId(Long restaurantBranchId);

}
