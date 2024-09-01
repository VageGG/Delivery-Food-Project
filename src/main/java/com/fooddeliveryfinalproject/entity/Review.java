package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private long reviewId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private byte rating;

    private String comment;
}
