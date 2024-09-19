package com.fooddeliveryfinalproject.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
public class DriverDto extends UserDto {

    private List<DeliveryDto> deliveriesDto;

}
