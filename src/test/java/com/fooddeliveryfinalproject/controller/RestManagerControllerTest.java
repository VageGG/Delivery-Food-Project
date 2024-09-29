package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.service.RestaurantManagerService;
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

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestManagerControllerTest {

    @Mock
    private RestaurantManagerService restaurantManagerService;

    @InjectMocks
    private RestaurantManagerController restController;

    private RestaurantManagerDto restaurantManagerDto;

    private Page<RestaurantManagerDto> restaurantManagerDtoPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurantManagerDto = new RestaurantManagerDto();
        restaurantManagerDto.setId(1L);
        restaurantManagerDto.setUsername("manager");
        restaurantManagerDto.setEmail("manager@example.com");
        restaurantManagerDto.setPassword("password");
        restaurantManagerDto.setPhoneNumber("1234567890");
        restaurantManagerDto.setRole(User.Role.RESTAURANT_MANAGER);

        restaurantManagerDtoPage = new PageImpl(Collections.singletonList(restaurantManagerDto));
    }

    @Test
    void getAllManagers_shouldReturnManagersList() {
        when(restaurantManagerService.getAllManagers(any(PageRequest.class))).thenReturn(restaurantManagerDtoPage);

        ResponseEntity<Iterable<RestaurantManagerDto>> response = restController.getAllManagers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantManagerDtoPage.getContent(), response.getBody());
        verify(restaurantManagerService, times(1)).getAllManagers(any(PageRequest.class));
    }

    @Test
    void getManager_shouldReturnManager() {
        when(restaurantManagerService.getRestaurantManager(1L)).thenReturn(restaurantManagerDto);

        ResponseEntity<RestaurantManagerDto> response = restController.getManager(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(restaurantManagerDto, response.getBody());
        verify(restaurantManagerService, times(1)).getRestaurantManager(1L);
    }

    @Test
    void updateManager_shouldReturnHttpStatusOk() {
        doNothing().when(restaurantManagerService).updateRestaurantManager(eq(1L), any(RestaurantManagerDto.class));

        ResponseEntity<HttpStatus> response = restController.updateManager(1L, restaurantManagerDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurantManagerService, times(1)).updateRestaurantManager(eq(1L), any(RestaurantManagerDto.class));
    }

    @Test
    void deleteManager_shouldReturnHttpStatusOk() {
        doNothing().when(restaurantManagerService).deleteRestaurantManager(1L);

        ResponseEntity<HttpStatus> response = restController.deleteManager(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(restaurantManagerService, times(1)).deleteRestaurantManager(1L);
    }

    @Test
    void approveManager_shouldReturnHttpStatusOk() {
        doNothing().when(restaurantManagerService).approveManager(1L);

        ResponseEntity<?> response = restController.approveManager(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Manager approved", response.getBody());
        verify(restaurantManagerService, times(1)).approveManager(1L);
    }

    @Test
    void rejectedManager_shouldReturnHttpStatusOk() {
        doNothing().when(restaurantManagerService).rejectedManager(1L);

        ResponseEntity<?> response = restController.rejectedManager(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Manager rejected", response.getBody());
        verify(restaurantManagerService, times(1)).rejectedManager(1L);
    }

    @Test
    void getAllPendingManagers_shouldReturnPendingManagersList() {
        when(restaurantManagerService.getAllPendingManagers()).thenReturn(Collections.singletonList(restaurantManagerDto));

        ResponseEntity<List<RestaurantManagerDto>> response = restController.getPendingManagers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(restaurantManagerService, times(1)).getAllPendingManagers();
    }
}
