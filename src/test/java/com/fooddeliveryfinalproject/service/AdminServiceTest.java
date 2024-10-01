package com.fooddeliveryfinalproject.service;

import com.fooddeliveryfinalproject.converter.AdminConverter;
import com.fooddeliveryfinalproject.entity.Admin;
import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.repository.AdminRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private AdminConverter adminConverter;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAdmins_ReturnsPageOfAdmins() {
        // given
        Admin admin = new Admin();
        AdminDto adminDto = new AdminDto();
        Page<Admin> adminPage = new PageImpl<>(Collections.singletonList(admin));

        when(adminRepo.findAll(any(Pageable.class))).thenReturn(adminPage);
        when(adminConverter.convertToModel(any(Admin.class), any(AdminDto.class))).thenReturn(adminDto);

        // when
        Page<AdminDto> result = adminService.getAllAdmins(Pageable.unpaged());

        // then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(adminDto, result.getContent().get(0));
        verify(adminRepo, times(1)).findAll(any(Pageable.class));
        verify(adminConverter, times(1)).convertToModel(admin, new AdminDto());
    }

    @Test
    void getAdmin_ReturnsAdminDto() {
        // given
        Long adminId = 1L;
        Admin admin = new Admin();
        AdminDto adminDto = new AdminDto();

        when(adminRepo.findById(adminId)).thenReturn(Optional.of(admin));
        when(adminConverter.convertToModel(any(Admin.class), any(AdminDto.class))).thenReturn(adminDto);

        // when
        AdminDto result = adminService.getAdmin(adminId);

        // then
        assertNotNull(result);
        assertEquals(adminDto, result);
        verify(adminRepo, times(1)).findById(adminId);
        verify(adminConverter, times(1)).convertToModel(admin, new AdminDto());
    }

    @Test
    void getAdmin_ThrowsRuntimeException_WhenAdminNotFound() {
        // given
        Long adminId = 1L;

        when(adminRepo.findById(adminId)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.getAdmin(adminId));
        assertEquals("Admin not found", exception.getMessage());
        verify(adminRepo, times(1)).findById(adminId);
    }

    @Test
    void addUser_SavesAdmin() throws NoSuchAlgorithmException {
        // given
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("admin");
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("Password-13");
        adminDto.setRole(User.Role.ADMIN);

        when(adminRepo.findByUsername(adminDto.getUsername())).thenReturn(Optional.empty());
        when(adminConverter.convertToEntity(any(AdminDto.class), any(Admin.class))).thenReturn(new Admin());

        // when
        adminService.addUser(adminDto);

        // then
        verify(adminRepo, times(1)).findByUsername(adminDto.getUsername());
        verify(adminRepo, times(1)).save(any(Admin.class));
    }

    @Test
    void addUser_ThrowsRuntimeException_WhenUsernameExists() throws NoSuchAlgorithmException {
        // given
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("admin");

        when(adminRepo.findByUsername(adminDto.getUsername())).thenReturn(Optional.of(new Admin()));

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.addUser(adminDto));
        assertEquals("Username has already been used", exception.getMessage());
        verify(adminRepo, times(1)).findByUsername(adminDto.getUsername());
    }

    @Test
    void updateAdmin_UpdatesExistingAdmin() {
        // given
        Long adminId = 1L;
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("updatedAdmin");
        adminDto.setEmail("updated@example.com");
        adminDto.setPassword("newPassword-13");
        Admin existingAdmin = new Admin();

        when(adminRepo.findById(adminId)).thenReturn(Optional.of(existingAdmin));

        // when
        adminService.updateAdmin(adminId, adminDto);

        // then
        verify(adminRepo, times(1)).findById(adminId);
        verify(adminRepo, times(1)).save(existingAdmin);
    }

    @Test
    void updateAdmin_ThrowsRuntimeException_WhenAdminNotFound() {
        // given
        Long adminId = 1L;
        AdminDto adminDto = new AdminDto();

        when(adminRepo.findById(adminId)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.updateAdmin(adminId, adminDto));
        assertEquals("Admin not found", exception.getMessage());
        verify(adminRepo, times(1)).findById(adminId);
    }

    @Test
    void deleteAdmin_DeletesAdmin() {
        // given
        Long adminId = 1L;

        // when
        adminService.deleteAdmin(adminId);

        // then
        verify(adminRepo, times(1)).deleteById(adminId);
    }
}

