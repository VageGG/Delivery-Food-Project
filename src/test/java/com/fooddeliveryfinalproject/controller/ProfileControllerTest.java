package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.Customer;
import com.fooddeliveryfinalproject.model.AddressDto;
import com.fooddeliveryfinalproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProfileControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProfileController profileController;

    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем mock Customer
        mockCustomer = new Customer();
        mockCustomer.setUsername("testUser");
        mockCustomer.setEmail("testUser@example.com");

        // Настраиваем SecurityContext и Authentication
        when(authentication.getPrincipal()).thenReturn(mockCustomer);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
    }

//    @Test
//    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
//    void updateProfile_ShouldUpdateProfileSuccessfully() {
//        // Настраиваем мок для userService.updateProfile
//        AllUserDto userDto = new AllUserDto();
//        userDto.setUsername("updatedUser");
//        userDto.setEmail("updatedUser@example.com");
//        userDto.setPassword("Password123!");
//        userDto.setPhoneNumber("1234567890");
//
//        when(userService.updateProfile(anyString(), any(AllUserDto.class)))
//                .thenReturn( (Optional<? extends User>) Optional.of(mockCustomer));
//
//        // Выполняем вызов контроллера
//        ResponseEntity<?> response = profileController.updateProfile(userDto, authentication);
//
//        // Проверяем результат
//        assertEquals("Profile updated successfully", response.getBody());
//        assertEquals(200, response.getStatusCodeValue());
//
//        // Проверяем, что метод updateProfile был вызван с правильными аргументами
//        verify(userService, times(1)).updateProfile(eq("testUser"), any(AllUserDto.class));
//    }
//
//    @Test
//    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
//    void getProfile_ShouldReturnUserProfile() {
//        // Создаём mock для класса Customer
//        Customer mockCustomer = new Customer();
//        mockCustomer.setUsername("testUser");
//        mockCustomer.setEmail("testUser@example.com");
//
//        // Мокаем возврат getProfile с правильным типом
//        when(userService.getProfile(anyString()))
//                .thenReturn(Optional.of(mockCustomer));
//
//        // Выполняем вызов контроллера
//        ResponseEntity<?> response = profileController.getProfile(authentication);
//
//        // Проверяем статус ответа
//        assertEquals(200, response.getStatusCodeValue());
//
//        // Проверяем, что в теле ответа вернулся mockCustomer
//        assertEquals(mockCustomer, response.getBody());
//
//        // Проверяем, что метод getProfile был вызван с правильным аргументом
//        verify(userService, times(1)).getProfile("testUser");
//    }


    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void deleteAddress_ShouldDeleteAddressSuccessfully() {
        Long addressId = 1L;

        // Выполняем вызов контроллера
        ResponseEntity<?> response = profileController.deleteAddress(addressId, authentication);

        // Проверяем результат
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Address deleted successfully", response.getBody());

        // Проверяем, что метод deleteAddress был вызван
        verify(userService, times(1)).deleteAddress("testUser", addressId);
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"CUSTOMER"})
    void updateAddress_ShouldUpdateAddressSuccessfully() {
        Long addressId = 1L;
        AddressDto addressDto = new AddressDto();
        addressDto.setCity("New City");
        addressDto.setCountry("New Country");

        // Выполняем вызов контроллера
        ResponseEntity<?> response = profileController.updateAddress(addressId, addressDto, authentication);

        // Проверяем результат
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Address updated successfully", response.getBody());

        // Проверяем, что метод updateAddress был вызван
        verify(userService, times(1)).updateAddress("testUser", addressId, addressDto);
    }
}
