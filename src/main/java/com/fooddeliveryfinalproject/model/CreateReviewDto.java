package com.fooddeliveryfinalproject.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateReviewDto {
    @Min(1)
    @Max(5)
    private byte rating;

    private String comment;

    private Long restaurantId;

    private Long customerId;
}
