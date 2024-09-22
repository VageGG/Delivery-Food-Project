package com.fooddeliveryfinalproject.model;

import lombok.*;

@Data
public class ReviewDto {

    private Long reviewId;

    private Byte rating;

    private String comment;
}
