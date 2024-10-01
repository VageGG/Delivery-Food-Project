package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.Address;
import com.fooddeliveryfinalproject.model.AllUserDto;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @Mock
    private User user;

    @InjectMocks
    private ProfileController profileController;

//    @Test
//    void getProfile_Success() {
//        // given
//        String username = "testUser";
//        AllUserDto userDto = new AllUserDto();
//        when(authentication.getPrincipal()).thenReturn(user);
//        when(user.getUsername()).thenReturn(username);
//        when(userService.getProfile(username)).thenReturn(Optional.of(userDto));
//
//        // when
//        ResponseEntity<?> response = profileController.getProfile(authentication);
//
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(userDto, response.getBody());
//        verify(userService, times(1)).getProfile(username);
//    }

//    @Test
//    void updateProfile_Success() {
//        // given
//        String username = "testUser";
//        AllUserDto updatedUserDto = new AllUserDto();
//        updatedUserDto.setUsername("newUsername");
//        updatedUserDto.setEmail("newEmail@example.com");
//        updatedUserDto.setPhoneNumber("123456789");
//
//        when(authentication.getPrincipal()).thenReturn(user);
//        when(user.getUsername()).thenReturn(username);
//        when(userService.updateProfile(username, updatedUserDto)).thenReturn(user);
//
//        // when
//        ResponseEntity<?> response = profileController.updateProfile(updatedUserDto, authentication);
//
//        // then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("Profile updated successfully", response.getBody());
//        verify(userService, times(1)).updateProfile(username, updatedUserDto);
//    }

    @Test
    void getAddressList_Success() {
        // given
        String username = "testUser";
        Address address = new Address(); // создайте и настройте объект Address
        List<Address> addressList = Collections.singletonList(address);

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        when(userService.getCustomerAddressesList(username)).thenReturn(addressList);

        // when
        ResponseEntity<List<Address>> response = profileController.getAddressList(authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addressList, response.getBody());
        verify(userService, times(1)).getCustomerAddressesList(username);
    }

    @Test
    void getAddressById_Success() {
        // given
        String username = "testUser";
        Long addressId = 1L;
        Address address = new Address(); // создайте и настройте объект Address

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        when(userService.getAddress(username, addressId)).thenReturn(Optional.of(address));

        // when
        ResponseEntity<?> response = profileController.getAddressById(addressId, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(address, response.getBody());
        verify(userService, times(1)).getAddress(username, addressId);
    }

    @Test
    void addAddress_Success() {
        // given
        String username = "testUser";
        AddressDto addressDto = new AddressDto(); // создайте и настройте объект AddressDto
        Address savedAddress = new Address(); // создайте и настройте объект Address

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        when(userService.addAddress(username, addressDto)).thenReturn(savedAddress);

        // when
        ResponseEntity<?> response = profileController.addAddress(addressDto, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedAddress, response.getBody());
        verify(userService, times(1)).addAddress(username, addressDto);
    }

    @Test
    void updateAddress_Success() {
        // given
        String username = "testUser";
        Long addressId = 1L;
        AddressDto updatedAddressDto = new AddressDto(); // создайте и настройте объект AddressDto

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        doNothing().when(userService).updateAddress(username, addressId, updatedAddressDto);

        // when
        ResponseEntity<?> response = profileController.updateAddress(addressId, updatedAddressDto, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Address updated successfully", response.getBody());
        verify(userService, times(1)).updateAddress(username, addressId, updatedAddressDto);
    }

    @Test
    void deleteAddress_Success() {
        // given
        String username = "testUser";
        Long addressId = 1L;

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getUsername()).thenReturn(username);
        doNothing().when(userService).deleteAddress(username, addressId);

        // when
        ResponseEntity<?> response = profileController.deleteAddress(addressId, authentication);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Address deleted successfully", response.getBody());
        verify(userService, times(1)).deleteAddress(username, addressId);
    }
}
