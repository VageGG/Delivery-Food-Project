package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.PaymentMethodConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.PaymentMethodRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepo paymentMethodRepo;

    private final PaymentMethodConverter paymentMethodConverter;

    private final CustomerRepo customerRepo;

    @Autowired
    public PaymentMethodService(PaymentMethodRepo paymentMethodRepo,
                                PaymentMethodConverter paymentMethodConverter,
                                CustomerRepo customerRepo) {
        this.paymentMethodRepo = paymentMethodRepo;
        this.paymentMethodConverter = paymentMethodConverter;
        this.customerRepo = customerRepo;
    }

    @Transactional(readOnly = true)
    public List<PaymentMethodDto> getAllPaymentMethod() {
        return paymentMethodRepo.findAll().stream()
                .map(paymentMethod -> paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaymentMethodDto getPaymentMethod(Long id) {
        return paymentMethodRepo.findById(id)
                .map(paymentMethod -> paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()))
               .orElseThrow(() -> new RuntimeException("Payment method not found"));
    }

    @Transactional
    public void createPaymentMethod(PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod());
        paymentMethodRepo.save(paymentMethod);
    }

    @Transactional
    public void updatePaymentMethod(Long id, PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodRepo.findById(id)
               .orElseThrow(() -> new RuntimeException("Payment method not found"));
        paymentMethodConverter.convertToEntity(paymentMethodDto, paymentMethod);
        paymentMethodRepo.save(paymentMethod);
    }

    @Transactional
    public void deletePaymentMethod(Long id) {
        paymentMethodRepo.deleteById(id);
    }

    @Transactional
    public void addPaymentMethod(Customer customer, PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod());
        paymentMethod.setCustomer(customer);
        paymentMethodRepo.save(paymentMethod);
    }

    @Transactional(readOnly = true)
    public List<PaymentMethodDto> listPaymentMethods(String username) {
        return customerRepo.findByUsername(username)
                .map(customer -> customer.getPaymentMethods().stream()
                        .map(paymentMethod -> paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public Optional<PaymentMethodDto> createPaymentMethod(String username, PaymentMethodDto paymentMethodDto) {
        Optional<Customer> customer = customerRepo.findByUsername(username);
        if (customer.isPresent()) {
            Customer cust = customer.get();
            PaymentMethod payment = paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod());
            payment.setCustomer(cust);
            PaymentMethod savedPaymentMethod = paymentMethodRepo.save(payment);
            return Optional.of(paymentMethodConverter.convertToModel(savedPaymentMethod, new PaymentMethodDto()));
        }
        return Optional.empty();
    }

    @Transactional(readOnly = true)
    public Optional<PaymentMethodDto> getPaymentMethods(String username, Long paymentMethodId) {
        return customerRepo.findByUsername(username)
                .flatMap(customer -> customer.getPaymentMethods().stream()
                        .map(pm -> paymentMethodConverter.convertToModel(pm, new PaymentMethodDto()))
                        .filter(pmDto -> pmDto != null && pmDto.getId().equals(paymentMethodId))
                        .findFirst());
    }

    @Transactional
    public Optional<PaymentMethodDto> updatePaymentMethod(String username, Long paymentMethodId, PaymentMethodDto updatedPaymentMethod) {
        Optional<PaymentMethodDto> paymentMethodOptional = getPaymentMethods(username, paymentMethodId);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethodDto paymentMethodDto = paymentMethodOptional.get();
            paymentMethodDto.setPaymentMethodType(updatedPaymentMethod.getPaymentMethodType());
            paymentMethodDto.setDetails(updatedPaymentMethod.getDetails());
            paymentMethodRepo.save(paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod()));
        }
        return paymentMethodOptional;
    }

    @Transactional
    public void deletePaymentMethod(String username, Long paymentMethodId) {
        Optional<PaymentMethodDto> paymentMethodDto = getPaymentMethods(username, paymentMethodId);

        if (paymentMethodDto.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodConverter.convertToEntity(paymentMethodDto.get(), new PaymentMethod());

            paymentMethodRepo.delete(paymentMethod);
        } else {
            throw new IllegalArgumentException("Payment method not found for ID: " + paymentMethodId);
        }
    }
}
