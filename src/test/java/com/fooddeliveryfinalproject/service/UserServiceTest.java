package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AddressConverter;
import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.entity.CustomerAddress;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.model.AllUserDto;
import com.fooddeliveryfinalproject.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private DriverRepo driverRepo;

    @Mock
    private RestaurantManagerRepo restaurantManagerRepo;

    @Mock
    private AddressRepo addressRepo;

    @Mock
    private CustomerAddressRepo customerAddressRepo;

    @Mock
    private AddressConverter addressConverter;

    @InjectMocks
    private UserService userService;

    private Customer customer;
    private Address existingAddress;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer();
        customer.setUsername("testUser");

        existingAddress = new Address();
        existingAddress.setId(1L);
        existingAddress.setCountry("US");
        existingAddress.setCity("Old City");
        existingAddress.setState("N/A");
        existingAddress.setStreet("Old Street");
        existingAddress.setHouseNumber("123");
        existingAddress.setApartmentNumber("A");

        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setAddress(existingAddress);
        customerAddress.setCustomer(customer);

        customer.setAddresses(Collections.singletonList(customerAddress));
    }

    @Test
    void loadUserByUsername_ShouldReturnUser_WhenUserExists() {
        String username = "testUser";
        Customer mockCustomer = new Customer();
        mockCustomer.setUsername(username);

        when(customerRepo.findByUsername(username)).thenReturn(Optional.of(mockCustomer));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    void loadUserByUsername_ShouldThrowException_WhenUserDoesNotExist() {
        String username = "nonExistentUser";

        when(customerRepo.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }

    @Test
    void updateProfile_ShouldUpdateUser_WhenUserExists() {
        String username = "testUser";
        AllUserDto updatedUserDto = new AllUserDto();
        updatedUserDto.setUsername("updatedUser");
        updatedUserDto.setEmail("updated@example.com");
        updatedUserDto.setPassword("NewPassw0rd!");

        Customer mockCustomer = new Customer();
        mockCustomer.setUsername(username);
        mockCustomer.setEmail("old@example.com");
        mockCustomer.setPassword("OldPass");

        when(customerRepo.findByUsername(username)).thenReturn(Optional.of(mockCustomer));

        userService.updateProfile(username, updatedUserDto);

        assertEquals("updatedUser", mockCustomer.getUsername());
        assertEquals("updated@example.com", mockCustomer.getEmail());
    }

    @Test
    void updateProfile_ShouldThrowException_WhenUserDoesNotExist() {
        String username = "nonExistentUser";
        AllUserDto updatedUserDto = new AllUserDto();

        when(customerRepo.findByUsername(username)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> {
            Optional<? extends User> result = userService.updateProfile(username, updatedUserDto);
            assertFalse(result.isPresent());
        });
    }

    @Test
    void getCustomerAddressesList_ShouldReturnAddresses_WhenCustomerExists() {
        String username = "testUser";
        Customer mockCustomer = new Customer();
        Address mockAddress = new Address();
        mockAddress.setId(1L);
        mockCustomer.setAddresses(List.of(new CustomerAddress(mockCustomer, mockAddress)));

        when(customerRepo.findByUsername(username)).thenReturn(Optional.of(mockCustomer));

        List<Address> addresses = userService.getCustomerAddressesList(username);

        assertFalse(addresses.isEmpty());
        assertEquals(1, addresses.size());
    }

    @Test
    void getCustomerAddressesList_ShouldReturnEmptyList_WhenCustomerDoesNotExist() {
        String username = "nonExistentUser";

        when(customerRepo.findByUsername(username)).thenReturn(Optional.empty());

        List<Address> addresses = userService.getCustomerAddressesList(username);

        assertTrue(addresses.isEmpty());
    }

    @Test
    void addAddress_ShouldAddAddress_WhenCustomerExists() {
        String username = "testUser";
        AddressDto newAddressDto = new AddressDto();
        newAddressDto.setCity("City");
        newAddressDto.setStreet("Street");

        Customer mockCustomer = new Customer();
        mockCustomer.setUsername(username);

        when(customerRepo.findByUsername(username)).thenReturn(Optional.of(mockCustomer));
        when(addressConverter.convertToEntity(any(), any())).thenReturn(new Address());
        when(addressRepo.save(any())).thenReturn(new Address());

        Address addedAddress = userService.addAddress(username, newAddressDto);

        assertNotNull(addedAddress);
        verify(customerAddressRepo, times(1)).save(any(CustomerAddress.class));
    }

    @Test
    void addAddress_ShouldThrowException_WhenCustomerDoesNotExist() {
        String username = "nonExistentUser";
        AddressDto newAddressDto = new AddressDto();

        when(customerRepo.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            userService.addAddress(username, newAddressDto);
        });
    }

    @Test
    void testDeleteAddress_Success() {
        when(customerRepo.findByUsername("testUser")).thenReturn(Optional.of(customer));
        when(addressRepo.findById(1L)).thenReturn(Optional.of(existingAddress));

        userService.deleteAddress("testUser", 1L);

        verify(addressRepo, times(1)).delete(existingAddress);
    }

    @Test
    void testDeleteAddress_CustomerNotFound() {
        when(customerRepo.findByUsername("testUser")).thenReturn(Optional.empty());

        userService.deleteAddress("testUser", 1L);

        verify(addressRepo, never()).delete(any());
    }

    @Test
    void testUpdateAddress_Success() {
        when(customerRepo.findByUsername("testUser")).thenReturn(Optional.of(customer));
        when(addressRepo.findById(1L)).thenReturn(Optional.of(existingAddress));

        AddressDto updatedAddressDto = new AddressDto();
        updatedAddressDto.setCity("New City");
        updatedAddressDto.setStreet("New Street");
        // Другие поля адреса...

        userService.updateAddress("testUser", 1L, updatedAddressDto);

        verify(addressRepo, times(1)).save(existingAddress);
        assertEquals("New City", existingAddress.getCity());
        assertEquals("New Street", existingAddress.getStreet());
    }

    @Test
    void testUpdateAddress_CustomerNotFound() {
        when(customerRepo.findByUsername("testUser")).thenReturn(Optional.empty());

        AddressDto updatedAddressDto = new AddressDto();
        updatedAddressDto.setCity("New City");

        userService.updateAddress("testUser", 1L, updatedAddressDto);

        verify(addressRepo, never()).save(any());
    }
}
