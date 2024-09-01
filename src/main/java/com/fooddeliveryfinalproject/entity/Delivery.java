package com.fooddeliveryfinalproject.entity;

import com.fooddeliveryfinalproject.DeliveryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    private Long deliveryId;
    @OneToOne(mappedBy = "delivery")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "deiver_id")
    private Driver driver;
    private String trackingNumber;
    private DeliveryStatus status;
    private String pickupLocation;
    private String deliveryLocation;
    private String dateTime;
}
