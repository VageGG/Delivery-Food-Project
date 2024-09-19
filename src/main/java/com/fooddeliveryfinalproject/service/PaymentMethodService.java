package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.PaymentMethodConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.PaymentMethodRepo;
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

    public PaymentMethodService(PaymentMethodRepo paymentMethodRepo,
                                PaymentMethodConverter paymentMethodConverter,
                                CustomerRepo customerRepo) {
        this.paymentMethodRepo = paymentMethodRepo;
        this.paymentMethodConverter = paymentMethodConverter;
        this.customerRepo = customerRepo;
    }

    public List<PaymentMethodDto> getAllPaymentMethod() {
        return paymentMethodRepo.findAll().stream()
                .map(paymentMethod -> paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()))
                .collect(Collectors.toList());
    }

    public PaymentMethodDto getPaymentMethods(Long id) {
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

    public List<PaymentMethod> listPaymentMethods(String email) {
        return customerRepo.findByUsername(email)
                .map(Customer::getPaymentMethods)
                .orElse(Collections.emptyList());
    }

    public Optional<PaymentMethod> createPaymentMethod(String email, PaymentMethodDto paymentMethodDto) {
        Optional<Customer> customer = customerRepo.findByUsername(email);
        if (customer.isPresent()) {
            Customer cust = customer.get();
            PaymentMethod payment = paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod());
            payment.setCustomer(cust);
            return Optional.of(paymentMethodRepo.save(payment));
        }
        return Optional.empty();
    }

    public Optional<PaymentMethod> getPaymentMethods(String email, Long paymentMethodId) {
        return customerRepo.findByUsername(email)
                .flatMap(customer -> customer.getPaymentMethods().stream()
                        .filter(pm -> pm.getId().equals(paymentMethodId))
                        .findFirst());
    }

    public Optional<PaymentMethod> updatePaymentMethod(String email, Long paymentMethodId, PaymentMethodDto updatedPaymentMethod) {
        Optional<PaymentMethod> paymentMethodOptional = getPaymentMethods(email, paymentMethodId);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            paymentMethod.setPaymentMethodType(updatedPaymentMethod.getPaymentMethodType());
            paymentMethod.setDetails(updatedPaymentMethod.getDetails());
            paymentMethodRepo.save(paymentMethod);
        }
        return paymentMethodOptional;
    }

    public void deletePaymentMethod(String email, Long paymentMethodId) {
        getPaymentMethods(email, paymentMethodId).ifPresent(paymentMethodRepo::delete);
    }
}
