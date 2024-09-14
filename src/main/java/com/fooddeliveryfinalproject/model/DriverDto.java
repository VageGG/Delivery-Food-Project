package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode
public class DriverDto extends UserDto {

    private List<DeliveryDto> deliveriesDto;

}
