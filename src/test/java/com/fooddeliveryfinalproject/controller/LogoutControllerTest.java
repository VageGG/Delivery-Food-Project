package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.service.BlacklistService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LogoutControllerTest {

    @InjectMocks
    private LogoutController logoutController;

    @Mock
    private JWTUtilService jwtUtil;

    @Mock
    private BlacklistService blacklistService;

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void logout_Successful() {
        // given
        String token = "test-token";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(blacklistService.isBlacklisted(token)).thenReturn(false);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(token, userDetails)).thenReturn(true);

        // when
        ResponseEntity<?> response = logoutController.logout(request);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Logged out successfully", response.getBody());
        verify(blacklistService, times(1)).addToBlacklist(token);
    }

    @Test
    void logout_TokenAlreadyInvalidated() {
        // given
        String token = "test-token";

        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(blacklistService.isBlacklisted(token)).thenReturn(true);

        // when
        ResponseEntity<?> response = logoutController.logout(request);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token already invalidated", response.getBody());
        verify(blacklistService, never()).addToBlacklist(token);
    }

    @Test
    void logout_InvalidToken() {
        // given
        String token = "test-token";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(jwtUtil.resolveToken(request)).thenReturn(token);
        when(blacklistService.isBlacklisted(token)).thenReturn(false);
        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(userService.loadUserByUsername(username)).thenReturn(userDetails);
        when(jwtUtil.validateToken(token, userDetails)).thenReturn(false);

        // when
        ResponseEntity<?> response = logoutController.logout(request);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid token", response.getBody());
        verify(blacklistService, never()).addToBlacklist(token);
    }

    @Test
    void logout_NoTokenProvided() {
        // given
        when(jwtUtil.resolveToken(request)).thenReturn(null);

        // when
        ResponseEntity<?> response = logoutController.logout(request);

        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No token provided", response.getBody());
    }
}
