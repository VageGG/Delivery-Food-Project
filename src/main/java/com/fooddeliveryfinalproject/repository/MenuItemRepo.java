package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem,Long> {
}
