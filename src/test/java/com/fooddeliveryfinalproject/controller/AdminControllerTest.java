package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.entity.User;
import com.fooddeliveryfinalproject.model.AdminDto;
import com.fooddeliveryfinalproject.service.AdminService;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @InjectMocks
    private AdminController adminController;

    private AdminDto adminDto;
    private List<AdminDto> adminDtoList;
    private Page<AdminDto> adminDtoPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        adminDto = new AdminDto();
        adminDto.setId(1L);
        adminDto.setUsername("admin");
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("Password-13");
        adminDto.setPhoneNumber("1234567890");
        adminDto.setRole(User.Role.ADMIN);

        adminDtoList = Arrays.asList(adminDto);
        adminDtoPage = new PageImpl<>(adminDtoList);
    }

    @Test
    void testGetAllAdmins() {
        int page = 0;
        int size = 10;

        when(adminService.getAllAdmins(PageRequest.of(page, size))).thenReturn(adminDtoPage);

        ResponseEntity<Iterable<AdminDto>> response = adminController.getAllAdmins(page, size);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminDtoList, response.getBody());
        verify(adminService, times(1)).getAllAdmins(PageRequest.of(page, size));
    }

    @Test
    void testGetAdmin() {
        Long id = 1L;

        when(adminService.getAdmin(id)).thenReturn(adminDto);

        ResponseEntity<AdminDto> response = adminController.getAdmin(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(adminDto, response.getBody());
        verify(adminService, times(1)).getAdmin(id);
    }

    @Test
    void testDeleteAdmin() {
        Long id = 1L;

        doNothing().when(adminService).deleteAdmin(id);

        ResponseEntity<HttpStatus> response = adminController.deleteAdmin(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(adminService, times(1)).deleteAdmin(id);
    }

    @Test
    void testGetExpectedRole() {
        User.Role role = adminController.getExpectedRole();
        assertEquals(User.Role.ADMIN, role);
    }
}

