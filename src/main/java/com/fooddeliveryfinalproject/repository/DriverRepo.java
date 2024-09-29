package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver,Long> {
    Optional<Driver> findByUsername(String username);

    Optional<Driver> findAllByStatus(RegistrationStatus registrationStatus);
}
