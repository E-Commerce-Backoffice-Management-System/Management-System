package com.managementSystem.review.dto;

import lombok.Getter;

@Getter
public class CreateReviewRequest {
    private Integer rating;
    private String content;

}
