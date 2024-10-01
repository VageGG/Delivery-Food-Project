package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Delivery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    private Long id;

    private OrderDto orderDto;

    private DriverDto driverDto;

    private String trackingNumber;

    private Delivery.DeliveryStatus status;

    private AddressDto pickupLocation;

    private AddressDto dropoffLocation;

    private LocalDateTime orderTime;

    private LocalDateTime pickupTime;

    private LocalDateTime dropoffTime;
}
