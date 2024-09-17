package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CustomerConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.*;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
public class CustomerService implements ValidUser<CustomerDto> {

    private final CustomerRepo customerRepo;

    private final CustomerConverter customerConverter;

    private final CustomerAddressService customerAddressService;

    private final AddressService addressService;

    private final PaymentMethodService paymentMethodService;

    private final CartService cartService;

    private final OrderService orderService;


    public CustomerService(CustomerRepo customerRepo,
                           CustomerConverter customerConverter,
                           AddressService addressService,
                           PaymentMethodService paymentMethodService,
                           CartService cartService,
                           OrderService orderService,
                           CustomerAddressService customerAddressService) {
        this.customerRepo = customerRepo;
        this.customerConverter = customerConverter;
        this.addressService = addressService;
        this.paymentMethodService = paymentMethodService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.customerAddressService = customerAddressService;
    }

    @Transactional(readOnly = true)
    public Page<CustomerDto> getAllCustomer(Pageable pageable) {
        return customerRepo.findAll(pageable).map(customer ->
                customerConverter.convertToModel(customer, new CustomerDto())
        );
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Page<OrderDto> getCustomerOrders(Long customerId, Pageable pageable) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return orderService.getOrdersByCustomer(customerId, pageable);
    }

    @Transactional(readOnly = true)
    public CartDto getCustomerCart(Long customerId) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        return cartService.getCartByCustomerId(customerId);
    }

    @Transactional
    public void addAddressForCustomer(Long customerId, AddressDto addressDto) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        Address newAddress = addressService.createAddress(addressDto);
        customerAddressService.createCustomerAddress(customer, newAddress);
    }

    @Transactional
    public void addPaymentMethod(Long customerId, PaymentMethodDto paymentMethodDto) {
        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        paymentMethodService.addPaymentMethod(customer, paymentMethodDto);
    }
}
