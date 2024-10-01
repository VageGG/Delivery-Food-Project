package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.CustomerConverter;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.CustomerDto;
import com.fooddeliveryfinalproject.repository.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private CustomerRepo customerRepo;
    private CustomerConverter customerConverter;
    private AddressService addressService;
    private CustomerAddressService customerAddressService;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerRepo = mock(CustomerRepo.class);
        customerConverter = mock(CustomerConverter.class);
        addressService = mock(AddressService.class);
        customerAddressService = mock(CustomerAddressService.class);
        customerService = new CustomerService(customerRepo, customerConverter, addressService, customerAddressService);
    }

    @Test
    void addUser_ShouldThrowExceptionWhenUsernameExists() throws NoSuchAlgorithmException {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUsername("existingUser");
        when(customerRepo.findByUsername(customerDto.getUsername())).thenReturn(Optional.of(new Customer()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customerService.addUser(customerDto));
    }

    @Test
    void addUser_ShouldSaveCustomerWhenValid() throws NoSuchAlgorithmException {
        // Arrange
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUsername("newUser");
        customerDto.setEmail("test@example.com");
        customerDto.setPassword("Password-123");
        customerDto.setRole(User.Role.CUSTOMER);
        when(customerRepo.findByUsername(customerDto.getUsername())).thenReturn(Optional.empty());
        when(customerConverter.convertToEntity(customerDto, new Customer())).thenReturn(new Customer());

        // Act
        customerService.addUser(customerDto);

        // Assert
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepo, times(1)).save(customerCaptor.capture());
        // Дополнительные проверки можно добавить здесь
    }

    @Test
    void updateCustomer_ShouldThrowExceptionWhenCustomerNotFound() {
        // Arrange
        Long customerId = 1L;
        CustomerDto customerDto = new CustomerDto();
        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> customerService.updateCustomer(customerId, customerDto));
    }

    @Test
    void deleteCustomer_ShouldCallDeleteById() {
        // Arrange
        Long customerId = 1L;

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepo, times(1)).deleteById(customerId);
    }
}
