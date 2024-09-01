package com.fooddeliveryfinalproject.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeService {
    private final String DEFAULT_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    public String getFormattedCurrentDateTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        return currentDateTime.format(formatter);
    }
}