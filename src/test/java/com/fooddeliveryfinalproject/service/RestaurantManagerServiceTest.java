package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.RestManagerConverter;
import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import com.fooddeliveryfinalproject.entity.RestaurantManager;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.RestaurantManagerDto;
import com.fooddeliveryfinalproject.repository.RestaurantManagerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantManagerServiceTest {

    @Mock
    private RestaurantManagerRepo restaurantManagerRepo;

    @Mock
    private RestManagerConverter restManagerConverter;

    @InjectMocks
    private RestaurantManagerService restaurantManagerService;

    private RestaurantManager restaurantManager;
    private RestaurantManagerDto restaurantManagerDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantManager = new RestaurantManager();
        restaurantManager.setId(1L);
        restaurantManager.setUsername("manager1");

        restaurantManagerDto = new RestaurantManagerDto();
        restaurantManagerDto.setUsername("manager1");
        restaurantManagerDto.setEmail("manager1@example.com");
        restaurantManagerDto.setPassword("Password-13");
        restaurantManagerDto.setRole(User.Role.RESTAURANT_MANAGER);
    }

    @Test
    void getAllManagers_ShouldReturnManagers() {
        when(restaurantManagerRepo.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(restaurantManager)));

        when(restManagerConverter.convertToModel(any(RestaurantManager.class), any(RestaurantManagerDto.class)))
                .thenReturn(restaurantManagerDto);

        var result = restaurantManagerService.getAllManagers(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void getRestaurantManager_ShouldReturnManager() {
        when(restaurantManagerRepo.findById(1L)).thenReturn(Optional.of(restaurantManager));
        when(restManagerConverter.convertToModel(any(RestaurantManager.class), any(RestaurantManagerDto.class)))
                .thenReturn(restaurantManagerDto);

        var result = restaurantManagerService.getRestaurantManager(1L);

        assertNotNull(result);
        assertEquals("manager1", result.getUsername());
    }

    @Test
    void addUser_ShouldThrowException_WhenEmailExists() {
        when(restaurantManagerRepo.findByUsername("manager1")).thenReturn(Optional.of(restaurantManager));

        Exception exception = assertThrows(RuntimeException.class, () -> restaurantManagerService.addUser(restaurantManagerDto));

        assertEquals("Email has already been used", exception.getMessage());
    }

    @Test
    void updateRestaurantManager_ShouldUpdateManager() {
        when(restaurantManagerRepo.findById(1L)).thenReturn(Optional.of(restaurantManager));

        restaurantManagerDto.setUsername("updatedManager");
        restaurantManagerService.updateRestaurantManager(1L, restaurantManagerDto);

        assertEquals("updatedManager", restaurantManager.getUsername());
    }

    @Test
    void approveManager_ShouldApproveManager() {
        when(restaurantManagerRepo.findById(1L)).thenReturn(Optional.of(restaurantManager));

        restaurantManagerService.approveManager(1L);

        assertEquals(RegistrationStatus.APPROVED, restaurantManager.getStatus());
    }

    @Test
    void rejectedManager_ShouldRejectManager() {
        when(restaurantManagerRepo.findById(1L)).thenReturn(Optional.of(restaurantManager));

        restaurantManagerService.rejectedManager(1L);

        assertEquals(RegistrationStatus.REJECTED, restaurantManager.getStatus());
    }

    @Test
    void deleteRestaurantManager_ShouldDeleteManager() {
        doNothing().when(restaurantManagerRepo).deleteById(1L);

        restaurantManagerService.deleteRestaurantManager(1L);

        verify(restaurantManagerRepo, times(1)).deleteById(1L);
    }
}
