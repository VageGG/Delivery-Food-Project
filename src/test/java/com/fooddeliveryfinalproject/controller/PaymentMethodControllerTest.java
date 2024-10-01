package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.PaymentMethodDto;
import com.fooddeliveryfinalproject.service.PaymentMethodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PaymentMethodControllerTest {

    @InjectMocks
    private PaymentMethodController paymentMethodController;

    @Mock
    private PaymentMethodService paymentMethodService;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPaymentMethods_Success() {
        // given
        String username = "testUser";
        List<PaymentMethodDto> paymentMethodDtos = Arrays.asList(new PaymentMethodDto(), new PaymentMethodDto());

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        when(paymentMethodService.listPaymentMethods(username)).thenReturn(paymentMethodDtos);

        // when
        ResponseEntity<List<PaymentMethodDto>> response = paymentMethodController.getPaymentMethods(authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentMethodDtos, response.getBody());
        verify(paymentMethodService, times(1)).listPaymentMethods(username);
    }

    @Test
    void createPaymentMethod_Success() {
        // given
        String username = "testUser";
        PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
        Optional<PaymentMethodDto> createdPaymentMethod = Optional.of(paymentMethodDto);

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);

        // Мокаем возврат значения для метода
        when(paymentMethodService.createPaymentMethod(username, paymentMethodDto)).thenReturn(createdPaymentMethod);

        // when
        ResponseEntity<?> response = paymentMethodController.createPaymentMethod(paymentMethodDto, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment method created successfully", response.getBody());
        verify(paymentMethodService, times(1)).createPaymentMethod(username, paymentMethodDto);
    }

    @Test
    void getPaymentMethod_Success() {
        // given
        String username = "testUser";
        Long paymentMethodId = 1L;
        PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
        Optional<PaymentMethodDto> paymentMethodOptional = Optional.of(paymentMethodDto);

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);

        // Мокаем возврат значения для метода
        when(paymentMethodService.getPaymentMethods(username, paymentMethodId)).thenReturn(paymentMethodOptional);

        // when
        ResponseEntity<?> response = paymentMethodController.getPaymentMethod(paymentMethodId, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(paymentMethodOptional, response.getBody());
        verify(paymentMethodService, times(1)).getPaymentMethods(username, paymentMethodId);
    }

    @Test
    void updatePaymentMethod_Success() {
        // given
        String username = "testUser";
        Long paymentMethodId = 1L;
        PaymentMethodDto paymentMethodDto = new PaymentMethodDto();
        Optional<PaymentMethodDto> updatedPaymentMethod = Optional.of(paymentMethodDto);

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);

        // Мокаем возврат значения для метода
        when(paymentMethodService.updatePaymentMethod(username, paymentMethodId, paymentMethodDto)).thenReturn(updatedPaymentMethod);

        // when
        ResponseEntity<?> response = paymentMethodController.updatePaymentMethod(paymentMethodId, paymentMethodDto, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment method updated successfully", response.getBody());
        verify(paymentMethodService, times(1)).updatePaymentMethod(username, paymentMethodId, paymentMethodDto);
    }


    @Test
    void deletePaymentMethod_Success() {
        // given
        String username = "testUser";
        Long paymentMethodId = 1L;

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        doNothing().when(paymentMethodService).deletePaymentMethod(username, paymentMethodId);

        // when
        ResponseEntity<?> response = paymentMethodController.deletePaymentMethod(paymentMethodId, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment method deleted successfully", response.getBody());
        verify(paymentMethodService, times(1)).deletePaymentMethod(username, paymentMethodId);
    }
}
