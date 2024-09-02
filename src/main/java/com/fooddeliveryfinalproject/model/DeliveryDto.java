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

    private String pickupLocation;

    private String deliveryLocation;

    private String dateTime;
}
