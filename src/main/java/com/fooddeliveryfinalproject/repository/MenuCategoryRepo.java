package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuCategoryRepo extends JpaRepository<MenuCategory, Long> {
}
