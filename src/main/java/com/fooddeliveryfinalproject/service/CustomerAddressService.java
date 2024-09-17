package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.CustomerAddress;
import com.fooddeliveryfinalproject.repository.CustomerAddressRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerAddressService {

    private final CustomerAddressRepo customerAddressRepo;

    public CustomerAddressService(CustomerAddressRepo customerAddressRepo) {
        this.customerAddressRepo = customerAddressRepo;
    }

    @Transactional
    public void createCustomerAddress(Customer customer, Address address) {
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomer(customer);
        customerAddress.setAddress(address);
        customerAddressRepo.save(customerAddress);
    }
}
