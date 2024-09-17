package com.fooddeliveryfinalproject.repository;

import com.fooddeliveryfinalproject.entity.CustomerAddress;
import com.fooddeliveryfinalproject.entity.CustomerAddressId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepo extends JpaRepository<CustomerAddress, CustomerAddressId> {
}
