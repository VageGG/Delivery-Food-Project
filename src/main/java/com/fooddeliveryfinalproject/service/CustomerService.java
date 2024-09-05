package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.*;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CartRepo orderCartRepo;
    private final OrderRepo orderRepo;
    private final DeliveryRepo deliveryRepo;
    private final RestaurantRepo restaurantRepo;
    private final ReviewRepo reviewRepo;
    private final PaymentRepo paymentRepo;
    private final PaymentMethodRepo paymentMethodRepo;

    private final CustomerConverter customerConverter;
    private final CartConverter orderCartConverter;
    private final OrderConverter orderConverter;
    private final DeliveryConverter deliveryConverter;
    private final RestaurantConverter restaurantConverter;
    private final ReviewConverter reviewConverter;
    private final PaymentConverter paymentConverter;
    private final PaymentMethodConverter paymentMethodConverter;

    public CustomerService(CustomerRepo customerRepo,
                           CustomerConverter customerConverter,
                           CartRepo orderCartRepo,
                           OrderRepo orderRepo,
                           DeliveryRepo deliveryRepo,
                           RestaurantRepo restaurantRepo,
                           ReviewRepo reviewRepo,
                           PaymentRepo paymentRepo,
                           PaymentMethodRepo paymentMethodRepo,
                           PaymentMethodConverter paymentMethodConverter,
                           CartConverter orderCartConverter,
                           OrderConverter orderConverter,
                           DeliveryConverter deliveryConverter,
                           RestaurantConverter restaurantConverter,
                           ReviewConverter reviewConverter,
                           PaymentConverter paymentConverter) {
        this.customerRepo = customerRepo;
        this.customerConverter = customerConverter;
        this.orderCartRepo = orderCartRepo;
        this.orderRepo = orderRepo;
        this.deliveryRepo = deliveryRepo;
        this.restaurantRepo = restaurantRepo;
        this.reviewRepo = reviewRepo;
        this.paymentRepo = paymentRepo;
        this.paymentMethodRepo = paymentMethodRepo;
        this.paymentMethodConverter = paymentMethodConverter;
        this.orderCartConverter = orderCartConverter;
        this.orderConverter = orderConverter;
        this.deliveryConverter = deliveryConverter;
        this.restaurantConverter = restaurantConverter;
        this.reviewConverter = reviewConverter;
        this.paymentConverter = paymentConverter;
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
        Customer customer = customerConverter.convertToEntity(customerDto, new Customer());
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
