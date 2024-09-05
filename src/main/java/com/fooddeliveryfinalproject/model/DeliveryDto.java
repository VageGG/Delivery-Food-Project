package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Delivery;
import lombok.Data;

@Data
public class DeliveryDto {

    private Long id;

    private OrderDto orderDto;

    private DriverDto driverDto;

    private String trackingNumber;

    private Delivery.DeliveryStatus status;

    private AddressDto pickupLocation;

    private AddressDto dropoffLocation;

    private String dateTime;
}
