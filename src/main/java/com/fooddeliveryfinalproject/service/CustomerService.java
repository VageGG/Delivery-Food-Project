package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CustomerConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private CustomerRepo customerRepo;

    private CustomerConverter customerConverter;

    public CustomerService(CustomerRepo customerRepo, CustomerConverter customerConverter) {
        this.customerRepo = customerRepo;
        this.customerConverter = customerConverter;
    }

    public CustomerDto getCustomer(Long id) {
        return customerConverter.convertToModel(customerRepo.findById(id).orElse(null), new CustomerDto());
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        return customerRepo.save(customer);
    }


}
