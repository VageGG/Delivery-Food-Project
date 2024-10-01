package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.CustomerAddress;
import com.fooddeliveryfinalproject.repository.CustomerAddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerAddressService {

    private final CustomerAddressRepo customerAddressRepo;

    @Autowired
    public CustomerAddressService(CustomerAddressRepo customerAddressRepo) {
        this.customerAddressRepo = customerAddressRepo;
    }

    @Transactional
    public void createCustomerAddress(Customer customer, Address address) {
        if (customerAddressRepo.existsByCustomerAndAddress(customer, address)) {
            throw new RuntimeException("CustomerAddress already exists");
        }

        CustomerAddress customerAddress = new CustomerAddress(customer, address);
        customerAddressRepo.save(customerAddress);
    }
}
