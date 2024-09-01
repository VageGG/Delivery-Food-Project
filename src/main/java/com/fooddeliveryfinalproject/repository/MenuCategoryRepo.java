package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepo extends JpaRepository<MenuCategory, Long> {
}
