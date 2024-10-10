package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.Delivery;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryStatusDto {
    Delivery.DeliveryStatus status;
}
