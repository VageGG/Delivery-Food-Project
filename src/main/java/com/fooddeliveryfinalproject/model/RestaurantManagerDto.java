package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RestaurantManagerDto extends  UserDto{

    private RegistrationStatus status;
}
