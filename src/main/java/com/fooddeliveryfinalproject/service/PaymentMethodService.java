package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.PaymentMethodConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.repository.PaymentMethodRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentMethodService {

    private final PaymentMethodRepo paymentMethodRepo;

    private final PaymentMethodConverter paymentMethodConverter;

    public PaymentMethodService(PaymentMethodRepo paymentMethodRepo, PaymentMethodConverter paymentMethodConverter) {
        this.paymentMethodRepo = paymentMethodRepo;
        this.paymentMethodConverter = paymentMethodConverter;
    }

    public List<PaymentMethodDto> getAllPaymentMethod() {
        return paymentMethodRepo.findAll().stream()
                .map(paymentMethod -> paymentMethodConverter.convertToModel(paymentMethod, new PaymentMethodDto()))
                .collect(Collectors.toList());
    }

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
}
