package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.*;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    private final CustomerConverter customerConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepo customerRepo,
                           CustomerConverter customerConverter) {
        this.customerRepo = customerRepo;
        this.customerConverter = customerConverter;
    }

    public List<CustomerDto> getAllCustomer() {
        return customerRepo.findAll().stream()
                .map(customer -> customerConverter.convertToModel(customer, new CustomerDto()))
                .collect(Collectors.toList());
    }
    public CustomerDto getCustomer(Long id) {
        return customerConverter.convertToModel(customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found")),
                new CustomerDto());
    }

    @Transactional
    public void createCustomer(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setUsername(customerDto.getUsername());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setRole(customerDto.getRole());
        customerRepo.save(customer);
    }

    @Transactional
    public void updateCustomer(Long id, CustomerDto customerDto) {
        Customer customerEntity = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerConverter.convertToEntity(customerDto, customerEntity);
        customerRepo.save(customerEntity);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepo.deleteById(id);
    }
}
