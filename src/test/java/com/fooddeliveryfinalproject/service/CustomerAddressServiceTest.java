package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.CustomerAddress;
import com.fooddeliveryfinalproject.repository.CustomerAddressRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerAddressServiceTest {

    private CustomerAddressRepo customerAddressRepo;
    private CustomerAddressService customerAddressService;

    @BeforeEach
    void setUp() {
        customerAddressRepo = mock(CustomerAddressRepo.class);
        customerAddressService = new CustomerAddressService(customerAddressRepo);
    }

    @Test
    void createCustomerAddress_ShouldThrowExceptionWhenAddressExists() {
        // Arrange
        Customer customer = new Customer();
        Address address = new Address();
        when(customerAddressRepo.existsByCustomerAndAddress(customer, address)).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            customerAddressService.createCustomerAddress(customer, address);
        });
    }

    @Test
    void createCustomerAddress_ShouldSaveCustomerAddressWhenNotExists() {
        // Arrange
        Customer customer = new Customer();
        Address address = new Address();
        when(customerAddressRepo.existsByCustomerAndAddress(customer, address)).thenReturn(false);

        // Act
        customerAddressService.createCustomerAddress(customer, address);

        // Assert
        ArgumentCaptor<CustomerAddress> customerAddressCaptor = ArgumentCaptor.forClass(CustomerAddress.class);
        verify(customerAddressRepo, times(1)).save(customerAddressCaptor.capture());
        CustomerAddress savedCustomerAddress = customerAddressCaptor.getValue();
        // Дополнительные проверки можно добавить, если необходимо
    }
}
