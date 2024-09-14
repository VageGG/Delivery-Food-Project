package com.fooddeliveryfinalproject.model;

import com.fooddeliveryfinalproject.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class RestaurantManagerDto extends  UserDto{

    private RestaurantDto restaurantDto;
}
