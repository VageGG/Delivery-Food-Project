package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
public class Admin extends User {

    @Builder
    public Admin(Long id, String username, String password, String email, String phoneNumber) {
        super(id, username, password, email, phoneNumber, Role.ADMIN);
    }
}
