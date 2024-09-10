package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "drivers")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Driver extends User {

    @OneToMany(mappedBy="driver")
    private List<Delivery> deliveries;

    @Builder
    public Driver(Long id, String username, String password, String email, String phoneNumber,
                  List<Delivery> deliveries) {
        super(id, username, password, email, phoneNumber, Role.DRIVER);
        this.deliveries = deliveries;
    }

}
