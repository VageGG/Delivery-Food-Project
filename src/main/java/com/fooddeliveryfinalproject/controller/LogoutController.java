package com.fooddeliveryfinalproject.controller;

import com.fooddeliveryfinalproject.service.BlacklistService;
import com.fooddeliveryfinalproject.service.JWTUtilService;
import com.fooddeliveryfinalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    private final JWTUtilService jwtUtil;
    private final BlacklistService blacklistService;
    private final UserService userService;

    @Autowired
    public LogoutController(JWTUtilService jwtUtil, BlacklistService blacklistService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.blacklistService = blacklistService;
        this.userService = userService;
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'DRIVER', 'RESTAURANT_MANAGER', 'ADMIN')")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Получаем токен из заголовка Authorization
        String token = jwtUtil.resolveToken(request);

        // Проверяем, что токен не пустой
        if (token != null) {

            if (blacklistService.isBlacklisted(token)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token already invalidated");
            }
            // Извлекаем имя пользователя из токена
            String username = jwtUtil.extractUsername(token);

            // Загружаем детали пользователя из UserDetailsService
            UserDetails userDetails = userService.loadUserByUsername(username);

            // Проверяем валидность токена
            if (jwtUtil.validateToken(token, userDetails)) {
                // Добавляем токен в черный список
                blacklistService.addToBlacklist(token);
                return ResponseEntity.ok("Logged out successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No token provided");
        }
    }
}