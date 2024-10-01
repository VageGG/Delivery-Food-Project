package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.PaymentMethodConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.Payment;
import com.fooddeliveryfinalproject.entity.PaymentMethod;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import com.fooddeliveryfinalproject.repository.PaymentMethodRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentMethodServiceTest {

    @Mock
    private PaymentMethodRepo paymentMethodRepo;

    @Mock
    private PaymentMethodConverter paymentMethodConverter;

    @Mock
    private CustomerRepo customerRepo;

    @InjectMocks
    private PaymentMethodService paymentMethodService;

    private Customer customer;
    private PaymentMethod paymentMethod;
    private PaymentMethodDto paymentMethodDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setId(1L);
        customer.setUsername("testUser");
        customer.setEmail("test@example.com");
        customer.setPassword("Test-1234");
        paymentMethod = new PaymentMethod();
        paymentMethod.setId(1L);
        paymentMethod.setPaymentMethodType(Payment.PaymentMethodType.CARD);
        paymentMethod.setDetails("2314343343");
        paymentMethodDto = new PaymentMethodDto();
        paymentMethodDto.setId(1L);
        paymentMethodDto.setPaymentMethodType(Payment.PaymentMethodType.CARD);
        paymentMethodDto.setDetails("2314343343");
    }

    @Test
    void getAllPaymentMethod_ShouldReturnPaymentMethodDtos() {
        when(paymentMethodRepo.findAll()).thenReturn(Collections.singletonList(paymentMethod));
        when(paymentMethodConverter.convertToModel(any(PaymentMethod.class), any(PaymentMethodDto.class)))
                .thenReturn(paymentMethodDto);

        var result = paymentMethodService.getAllPaymentMethod();

        assertEquals(1, result.size());
        assertEquals(paymentMethodDto, result.get(0));
        verify(paymentMethodRepo).findAll();
    }

    @Test
    void getPaymentMethod_ShouldReturnPaymentMethodDto() {
        when(paymentMethodRepo.findById(1L)).thenReturn(Optional.of(paymentMethod));
        when(paymentMethodConverter.convertToModel(any(PaymentMethod.class), any(PaymentMethodDto.class)))
                .thenReturn(paymentMethodDto);

        var result = paymentMethodService.getPaymentMethod(1L);

        assertEquals(paymentMethodDto, result);
        verify(paymentMethodRepo).findById(1L);
    }

    @Test
    void getPaymentMethod_NotFound_ShouldThrowException() {
        when(paymentMethodRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentMethodService.getPaymentMethod(1L);
        });

        assertEquals("Payment method not found", exception.getMessage());
    }

    @Test
    void createPaymentMethod_ShouldSavePaymentMethod() {
        when(paymentMethodConverter.convertToEntity(any(PaymentMethodDto.class), any(PaymentMethod.class)))
                .thenReturn(paymentMethod);

        paymentMethodService.createPaymentMethod(paymentMethodDto);

        verify(paymentMethodRepo).save(paymentMethod);
    }

    @Test
    void updatePaymentMethod_ShouldUpdatePaymentMethod() {
        when(paymentMethodRepo.findById(1L)).thenReturn(Optional.of(paymentMethod));
        when(paymentMethodConverter.convertToEntity(any(PaymentMethodDto.class), any(PaymentMethod.class)))
                .thenReturn(paymentMethod);

        paymentMethodService.updatePaymentMethod(1L, paymentMethodDto);

        verify(paymentMethodRepo).save(paymentMethod);
    }

    @Test
    void updatePaymentMethod_NotFound_ShouldThrowException() {
        when(paymentMethodRepo.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentMethodService.updatePaymentMethod(1L, paymentMethodDto);
        });

        assertEquals("Payment method not found", exception.getMessage());
    }

    @Test
    void deletePaymentMethod_ShouldCallDelete() {
        paymentMethodService.deletePaymentMethod(1L);

        verify(paymentMethodRepo).deleteById(1L);
    }

    @Test
    void addPaymentMethod_ShouldSavePaymentMethodWithCustomer() {
        when(customerRepo.findByUsername("testUser")).thenReturn(Optional.of(customer));
        when(paymentMethodConverter.convertToEntity(any(PaymentMethodDto.class), any(PaymentMethod.class)))
                .thenReturn(paymentMethod);

        paymentMethodService.addPaymentMethod(customer, paymentMethodDto);

        verify(paymentMethodRepo).save(paymentMethod);
        assertEquals(customer, paymentMethod.getCustomer());
    }

    @Test
    void listPaymentMethods_ShouldReturnPaymentMethods() {
        when(customerRepo.findByUsername("testUser")).thenReturn(Optional.of(customer));

        PaymentMethod anotherPaymentMethod = new PaymentMethod();
        customer.setPaymentMethods(Arrays.asList(paymentMethod, anotherPaymentMethod));

        when(paymentMethodConverter.convertToModel(any(PaymentMethod.class), any(PaymentMethodDto.class)))
                .thenReturn(paymentMethodDto);

        var result = paymentMethodService.listPaymentMethods("testUser");

        assertEquals(2, result.size());
        assertTrue(result.contains(paymentMethodDto));
    }

    @Test
    void createPaymentMethod_WithUsername_ShouldReturnOptional() {
        when(customerRepo.findByUsername("testUser")).thenReturn(Optional.of(customer));
        when(paymentMethodConverter.convertToEntity(any(PaymentMethodDto.class), any(PaymentMethod.class)))
                .thenReturn(paymentMethod);
        when(paymentMethodRepo.save(any(PaymentMethod.class))).thenReturn(paymentMethod);
        when(paymentMethodConverter.convertToModel(any(PaymentMethod.class), any(PaymentMethodDto.class)))
                .thenReturn(paymentMethodDto);

        var result = paymentMethodService.createPaymentMethod("testUser", paymentMethodDto);

        assertTrue(result.isPresent());
        assertEquals(paymentMethodDto, result.get());
    }

    @Test
    void createPaymentMethod_WithInvalidUsername_ShouldReturnEmpty() {
        when(customerRepo.findByUsername("invalidUser")).thenReturn(Optional.empty());

        var result = paymentMethodService.createPaymentMethod("invalidUser", paymentMethodDto);

        assertFalse(result.isPresent());
    }

}
