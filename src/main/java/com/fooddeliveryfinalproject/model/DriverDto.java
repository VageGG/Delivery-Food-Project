package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.RegistrationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class DriverDto extends UserDto {

    private RegistrationStatus status;

}
