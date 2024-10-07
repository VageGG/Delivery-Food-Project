package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.PaymentMethodConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.PaymentMethodRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
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
    public PaymentMethodDto getPaymentMethod(@Min(1) Long id) {
        return paymentMethodRepo.findById(id)
                .map(paymentMethod -> paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()))
               .orElseThrow(() -> new RuntimeException("Payment method not found"));
    }

    @Transactional
    public void createPaymentMethod(@Valid PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod());
        paymentMethodRepo.save(paymentMethod);
    }

    @Transactional
    public void updatePaymentMethod(@Min(1) Long id, @Valid PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodRepo.findById(id)
               .orElseThrow(() -> new RuntimeException("Payment method not found"));
        paymentMethodConverter.convertToEntity(paymentMethodDto, paymentMethod);
        paymentMethodRepo.save(paymentMethod);
    }

    @Transactional
    public void deletePaymentMethod(@Min(1) Long id) {
        paymentMethodRepo.deleteById(id);
    }

    @Transactional
    public void addPaymentMethod(Customer customer, @Valid PaymentMethodDto paymentMethodDto) {
        PaymentMethod paymentMethod = paymentMethodConverter.convertToEntity(paymentMethodDto, new PaymentMethod());
        paymentMethod.setCustomer(customer);
        paymentMethodRepo.save(paymentMethod);
    }

    @Transactional(readOnly = true)
    public List<PaymentMethodDto> listPaymentMethods(@NotNull String username) {
        return customerRepo.findByUsername(username)
                .map(customer -> customer.getPaymentMethods().stream()
                        .map(paymentMethod -> paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Transactional
    public Optional<PaymentMethodDto> createPaymentMethod(@NotNull String username, @Valid PaymentMethodDto paymentMethodDto) {
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
    public Optional<PaymentMethodDto> getPaymentMethods(@NotNull String username, @Valid Long paymentMethodId) {
        return customerRepo.findByUsername(username)
                .flatMap(customer -> customer.getPaymentMethods().stream()
                        .map(pm -> paymentMethodConverter.convertToModel(pm, new PaymentMethodDto()))
                        .filter(pmDto -> pmDto != null && pmDto.getId().equals(paymentMethodId))
                        .findFirst());
    }

    @Transactional
    public Optional<PaymentMethodDto> updatePaymentMethod(@NotNull String username, @Min(1) Long paymentMethodId, @Valid PaymentMethodDto updatedPaymentMethod) {
        Optional<PaymentMethodDto> paymentMethodOptional = getPaymentMethods(username, paymentMethodId);

        if (paymentMethodOptional.isPresent()) {
            PaymentMethodDto paymentMethodDto = paymentMethodOptional.get();

            Optional<PaymentMethod> existingPaymentMethodOptional = paymentMethodRepo.findById(paymentMethodId);

            if (existingPaymentMethodOptional.isPresent()) {
                PaymentMethod existingPaymentMethod = existingPaymentMethodOptional.get();

                existingPaymentMethod.setPaymentMethodType(updatedPaymentMethod.getPaymentMethodType());
                existingPaymentMethod.setDetails(updatedPaymentMethod.getDetails());

                paymentMethodRepo.save(existingPaymentMethod);

                return Optional.of(paymentMethodConverter.convertToModel(existingPaymentMethod, new PaymentMethodDto()));
            }
        }
        return Optional.empty();
    }

    @Transactional
    public void deletePaymentMethod(@NotNull String username, @Min(1) Long paymentMethodId) {
        Optional<PaymentMethodDto> paymentMethodDto = getPaymentMethods(username, paymentMethodId);

        if (paymentMethodDto.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodConverter.convertToEntity(paymentMethodDto.get(), new PaymentMethod());

            paymentMethodRepo.delete(paymentMethod);
        } else {
            throw new IllegalArgumentException("Payment method not found for ID: " + paymentMethodId);
        }
    }
}
