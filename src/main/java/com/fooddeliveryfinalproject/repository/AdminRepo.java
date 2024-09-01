package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin findByUserAndPassword(String username, String password);
}
