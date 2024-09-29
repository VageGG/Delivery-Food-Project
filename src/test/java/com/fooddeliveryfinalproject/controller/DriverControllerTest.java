package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.service.DriverService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DriverControllerTest {

    @Mock
    private DriverService driverService;

    @InjectMocks
    private DriverController driverController;

    private DriverDto driverDto;
    private Page<DriverDto> driverDtoPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        driverDto = new DriverDto();
        driverDto.setId(1L);
        driverDto.setUsername("test_driver");
        driverDto.setEmail("test@example.com");
        driverDto.setPassword("test_Password13");
        driverDto.setPhoneNumber("1234567890");
        driverDto.setRole(User.Role.DRIVER);

        driverDtoPage = new PageImpl<>(Collections.singletonList(driverDto));
    }

    @Test
    void getAllDrivers_ShouldReturnDriverList() {
        when(driverService.getAllDriver(any(PageRequest.class))).thenReturn(driverDtoPage);

        ResponseEntity<Iterable<DriverDto>> response = driverController.getAllDrivers(0, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, ((List<DriverDto>) response.getBody()).size());
        verify(driverService, times(1)).getAllDriver(any(PageRequest.class));
    }

    @Test
    void getDriver_ShouldReturnDriver() {
        when(driverService.getDriverById(1L)).thenReturn(driverDto);

        ResponseEntity<DriverDto> response = driverController.getDriver(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(driverDto, response.getBody());
        verify(driverService, times(1)).getDriverById(1L);
    }

    @Test
    void updateDriver_ShouldReturnHttpStatusOk() {
        doNothing().when(driverService).updateDriver(eq(1L), any(DriverDto.class));

        ResponseEntity<HttpStatus> response = driverController.updateDriver(1L, driverDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(driverService, times(1)).updateDriver(eq(1L), any(DriverDto.class));
    }

    @Test
    void deleteDriver_ShouldReturnHttpStatusOk() {
        doNothing().when(driverService).deleteDriver(1L);

        ResponseEntity<HttpStatus> response = driverController.deleteDriver(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(driverService, times(1)).deleteDriver(1L);
    }

    @Test
    void approveDriver_ShouldReturnOk() {
        doNothing().when(driverService).approveDriver(1L);

        ResponseEntity<?> response = driverController.approveDriver(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver approved", response.getBody());
        verify(driverService, times(1)).approveDriver(1L);
    }

    @Test
    void rejectedDriver_ShouldReturnOk() {
        doNothing().when(driverService).rejectedDriver(1L);

        ResponseEntity<?> response = driverController.rejectedDriver(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Driver rejected", response.getBody());
        verify(driverService, times(1)).rejectedDriver(1L);
    }

    @Test
    void getPendingDrivers_ShouldReturnPendingDriverList() {
        when(driverService.getAllPendingDrivers()).thenReturn(Collections.singletonList(driverDto));

        ResponseEntity<List<DriverDto>> response = driverController.getPendingDrivers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(driverService, times(1)).getAllPendingDrivers();
    }
}

