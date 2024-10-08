package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "drivers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Driver extends User {

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @OneToMany(mappedBy="driver")
    private List<Delivery> deliveries;

    @Builder
    public Driver(Long id, String username, String password, String email, String phoneNumber,
                  List<Delivery> deliveries) {
        super(id, username, password, email, phoneNumber, Role.DRIVER);
        this.deliveries = deliveries;
    }

}
