package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.model.AllUserDto;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtilService jwtUtilService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    private AllUserDto userDto;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDto = new AllUserDto();
        userDto.setUsername("testUser");
        userDto.setPassword("Password-13");

        userDetails = mock(UserDetails.class);
    }

    @Test
    void login_ShouldReturnJwtToken_WhenCredentialsAreCorrect() throws Exception {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userService.loadUserByUsername(userDto.getUsername())).thenReturn(userDetails);
        when(jwtUtilService.generateToken(userDetails)).thenReturn("jwtToken");

        ResponseEntity<?> response = loginController.login(userDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("jwtToken", response.getBody());
    }

    @Test
    void login_ShouldThrowException_WhenBadCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        Exception exception = null;
        try {
            loginController.login(userDto);
        } catch (Exception e) {
            exception = e;
        }

        assertEquals("Incorrect username or password", exception.getMessage());
    }
}
