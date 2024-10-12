package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CustomerConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Validated
public class CustomerService implements ValidUser<CustomerDto> {

    private final CustomerRepo customerRepo;

    private final CustomerConverter customerConverter;

    private final CustomerAddressService customerAddressService;

    private final AddressService addressService;

    @Autowired
    public CustomerService(CustomerRepo customerRepo,
                           CustomerConverter customerConverter,
                           AddressService addressService,
                           CustomerAddressService customerAddressService) {
        this.customerRepo = customerRepo;
        this.customerConverter = customerConverter;
        this.addressService = addressService;
        this.customerAddressService = customerAddressService;
    }

    @Transactional(readOnly = true)
    public Page<CustomerDto> getAllCustomer(Pageable pageable) {
        return customerRepo.findAll(pageable).map(customer ->
                customerConverter.convertToModel(customer, new CustomerDto())
        );
    }

    @Transactional(readOnly = true)
    public CustomerDto getCustomer(@Min(1) Long id) {
        return customerConverter.convertToModel(customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found")),
                new CustomerDto());
    }

    @Override
    @Transactional
    public void addUser(CustomerDto customerDto) throws NoSuchAlgorithmException {
        Optional<Customer> existingUser = customerRepo.findByUsername(customerDto.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username has already been used");
        }

        if (!customerDto.getRole().equals(User.Role.CUSTOMER)) {
            throw new RuntimeException("Role mismatch. Customer role must be CUSTOMER");
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
    public void updateCustomer(@Min(1) Long id, @Valid CustomerDto customerDto) {
        Customer customerEntity = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        customerEntity.setUsername(customerDto.getUsername());

        if (isEmailValid(customerDto.getEmail())) {
            customerEntity.setEmail(customerDto.getEmail());
        }

        if (!isPasswordValid(customerDto.getPassword())) {
            String pw_hash = BCrypt.hashpw(customerDto.getPassword(), BCrypt.gensalt(12));
            customerEntity.setPassword(pw_hash);
        }

        customerEntity.setPhoneNumber(customerDto.getPhoneNumber());
        customerEntity.setRole(customerDto.getRole());
        customerRepo.save(customerEntity);
    }

    @Transactional
    public void deleteCustomer(@Min(1) Long id) {
        customerRepo.deleteById(id);
    }

    @Transactional
    public void addAddressForCustomer(@Min(1) Long customerId, @Valid AddressDto addressDto) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Address newAddress = addressService.createAddress(addressDto);
        customerAddressService.createCustomerAddress(customer, newAddress);
    }
}
