package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.*;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.repository.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService implements ValidUser<CustomerDto> {

    private final CustomerRepo customerRepo;

    private final CustomerConverter customerConverter;


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

    @Override
    @Transactional
    public void addUser(CustomerDto customerDto) throws NoSuchAlgorithmException {
        Optional<Customer> existingUser = customerRepo.findByEmail(customerDto.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email has already been used");
        }

        if (!isEmailValid(customerDto.getEmail())) {
            throw new RuntimeException("email is invalid");
        }

        if (!isPasswordValid(customerDto.getPassword())) {
            throw new RuntimeException("password is invalid");
        }

        if (customerDto.getUsername() == null) {
            throw new RuntimeException("name must be specified");
        }
        String pw_hash = BCrypt.hashpw(customerDto.getPassword(), BCrypt.gensalt(12));
        customerDto.setPassword(pw_hash);

        customerRepo.save(customerConverter.convertToEntity(customerDto, new Customer()));
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
