package com.fooddeliveryfinalproject.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlacklistServiceTest {

    private BlacklistService blacklistService;

    @BeforeEach
    void setUp() {
        blacklistService = new BlacklistService();
    }

    @Test
    void addToBlacklist_ShouldAddToken() {
        // Arrange
        String token = "testToken";

        // Act
        blacklistService.addToBlacklist(token);

        // Assert
        assertTrue(blacklistService.isBlacklisted(token), "Token should be blacklisted.");
    }

    @Test
    void isBlacklisted_ShouldReturnFalseForNonBlacklistedToken() {
        // Arrange
        String token = "nonBlacklistedToken";

        // Act & Assert
        assertFalse(blacklistService.isBlacklisted(token), "Token should not be blacklisted.");
    }

    @Test
    void isBlacklisted_ShouldReturnTrueForBlacklistedToken() {
        // Arrange
        String token = "testToken";
        blacklistService.addToBlacklist(token);

        // Act & Assert
        assertTrue(blacklistService.isBlacklisted(token), "Token should be blacklisted.");
    }
}

