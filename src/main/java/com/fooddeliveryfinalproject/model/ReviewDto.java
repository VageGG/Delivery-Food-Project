package com.fooddeliveryfinalproject.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long reviewId;

    @Min(1)
    @Max(5)
    private Byte rating;

    private String comment;
}
