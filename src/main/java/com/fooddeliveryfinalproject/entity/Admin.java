package com.fooddeliveryfinalproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
public class Admin extends User {

    @Builder
    public Admin(Long id, String username, String password, String email, String phoneNumber) {
        super(id, username, password, email, phoneNumber, Role.ADMIN);
    }
}
