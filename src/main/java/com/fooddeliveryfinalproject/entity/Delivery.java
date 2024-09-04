package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long deliveryId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @ManyToOne
    @JoinColumn(name = "pickup_address_id")
    private Address pickupLocation;

    @ManyToOne
    @JoinColumn(name = "dropoff_address_id")
    private Address dropoffLocation;

    @Column(name = "delivery_time")
    private String dateTime;

    public enum DeliveryStatus {
        PREPARING,
        PICKED_UP,
        DELIVERING,
        DELIVERED
    }
}
