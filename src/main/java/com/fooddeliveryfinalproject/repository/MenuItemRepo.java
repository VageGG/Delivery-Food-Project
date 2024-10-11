package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import com.fooddeliveryfinalproject.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem,Long> {
    Optional<MenuItem> findByMenuItemIdAndMenuCategory(Long itemId, MenuCategory menuCategory);
}
