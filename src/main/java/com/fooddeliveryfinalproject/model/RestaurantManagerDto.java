package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestaurantManagerDto extends  UserDto{

    private RegistrationStatus status;
}
