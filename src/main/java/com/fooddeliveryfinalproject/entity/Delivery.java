package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    // Pickup location as the address of the restaurant branch
    @ManyToOne
    @JoinColumn(name = "pickup_address_id")
    private Address pickupLocation;

    // Dropoff location as the address of the customer
    @ManyToOne
    @JoinColumn(name = "dropoff_address_id")
    private Address dropoffLocation;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    @Column(name = "pickup_time")
    private LocalDateTime pickupTime;

    @Column(name = "dropoff_time")
    private LocalDateTime dropoffTime;

    public enum DeliveryStatus {
        PREPARING,
        PICKED_UP,
        DELIVERING,
        DELIVERED
    }
}
