package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDto customerDto;
    private AddressDto addressDto;
    private List<CustomerDto> customerDtoList;
    private Page<CustomerDto> customerDtoPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setUsername("testUser");
        customerDto.setEmail("test@example.com");
        customerDto.setPassword("Test--123");
        customerDto.setPhoneNumber("1234567890");
        customerDto.setRole(User.Role.CUSTOMER);

        customerDtoList = Arrays.asList(customerDto);
        customerDtoPage = new PageImpl<>(customerDtoList);

        addressDto = new AddressDto();
        addressDto.setCountry("US");
        addressDto.setCity("New York");
        addressDto.setState("NY");
        addressDto.setStreet("123 Main St");
        addressDto.setHouseNumber("40");
        addressDto.setApartmentNumber("A");
    }

    @Test
    void testGetAllCustomers() {
        int page = 0;
        int size = 10;

        when(customerService.getAllCustomer(PageRequest.of(page, size))).thenReturn(customerDtoPage);

        ResponseEntity<List<CustomerDto>> response = customerController.getAllCustomers(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDtoList, response.getBody());
        verify(customerService, times(1)).getAllCustomer(PageRequest.of(page, size));
    }

    @Test
    void testGetCustomer() {
        Long id = 1L;

        when(customerService.getCustomer(id)).thenReturn(customerDto);

        ResponseEntity<CustomerDto> response = customerController.getCustomer(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDto, response.getBody());
        verify(customerService, times(1)).getCustomer(id);
    }

    @Test
    void testUpdateCustomer() {
        Long id = 1L;

        doNothing().when(customerService).updateCustomer(id, customerDto);

        ResponseEntity<HttpStatus> response = customerController.updateCustomer(id, customerDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).updateCustomer(id, customerDto);
    }

    @Test
    void testDeleteCustomer() {
        Long id = 1L;

        doNothing().when(customerService).deleteCustomer(id);

        ResponseEntity<HttpStatus> response = customerController.deleteCustomer(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(id);
    }

    @Test
    void testAddAddress() {
        Long customerId = 1L;

        doNothing().when(customerService).addAddressForCustomer(customerId, addressDto);

        ResponseEntity<HttpStatus> response = customerController.addAddress(customerId, addressDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).addAddressForCustomer(customerId, addressDto);
    }

    @Test
    void testGetExpectedRole() {
        assertEquals(User.Role.CUSTOMER, customerController.getExpectedRole());
    }
}

