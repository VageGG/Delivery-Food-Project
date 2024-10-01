package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Menu;
import com.fooddeliveryfinalproject.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuCategoryRepo extends JpaRepository<MenuCategory, Long> {
    List<MenuCategory> findAllByMenu_RestaurantBranch_RestBranchId(Long branchId);

    Optional<MenuCategory> findByIdAndMenu(Long categoryId, Menu menu);
}
