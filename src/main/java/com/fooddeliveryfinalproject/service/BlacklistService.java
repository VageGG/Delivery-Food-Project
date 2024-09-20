package com.fooddeliveryfinalproject.service;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class BlacklistService {

    private final Set<String> blacklistedTokens = new HashSet<>();

    // Добавляем токен в черный список
    public void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    // Проверяем, находится ли токен в черном списке
    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
