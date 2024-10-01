package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.DriverConverter;
import com.fooddeliveryfinalproject.entity.Driver;
import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.DriverDto;
import com.fooddeliveryfinalproject.repository.DriverRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DriverServiceTest {

    private DriverRepo driverRepo;
    private DriverConverter driverConverter;
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        driverRepo = mock(DriverRepo.class);
        driverConverter = mock(DriverConverter.class);
        driverService = new DriverService(driverRepo, driverConverter);
    }

    @Test
    void addUser_ShouldThrowExceptionWhenUsernameExists() throws NoSuchAlgorithmException {
        // Arrange
        DriverDto driverDto = new DriverDto();
        driverDto.setUsername("existingUser");
        when(driverRepo.findByUsername(driverDto.getUsername())).thenReturn(Optional.of(new Driver()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> driverService.addUser(driverDto));
    }

    @Test
    void addUser_ShouldSaveDriverWhenValid() throws NoSuchAlgorithmException {
        // Arrange
        DriverDto driverDto = new DriverDto();
        driverDto.setUsername("newUser");
        driverDto.setEmail("test@example.com");
        driverDto.setPassword("Password-123");
        driverDto.setRole(User.Role.DRIVER);
        when(driverRepo.findByUsername(driverDto.getUsername())).thenReturn(Optional.empty());
        when(driverConverter.convertToEntity(driverDto, new Driver())).thenReturn(new Driver());

        // Act
        driverService.addUser(driverDto);

        // Assert
        ArgumentCaptor<Driver> driverCaptor = ArgumentCaptor.forClass(Driver.class);
        verify(driverRepo, times(1)).save(driverCaptor.capture());
        // Дополнительные проверки можно добавить здесь
    }

    @Test
    void updateDriver_ShouldThrowExceptionWhenDriverNotFound() {
        // Arrange
        Long driverId = 1L;
        DriverDto driverDto = new DriverDto();
        when(driverRepo.findById(driverId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> driverService.updateDriver(driverId, driverDto));
    }

    @Test
    void approveDriver_ShouldUpdateStatusToApproved() {
        // Arrange
        Long driverId = 1L;
        Driver driver = new Driver();
        when(driverRepo.findById(driverId)).thenReturn(Optional.of(driver));

        // Act
        driverService.approveDriver(driverId);

        // Assert
        verify(driverRepo, times(1)).save(driver);
        assert (driver.getStatus() == RegistrationStatus.APPROVED);
    }

    @Test
    void deleteDriver_ShouldCallDeleteById() {
        // Arrange
        Long driverId = 1L;

        // Act
        driverService.deleteDriver(driverId);

        // Assert
        verify(driverRepo, times(1)).deleteById(driverId);
    }
}
