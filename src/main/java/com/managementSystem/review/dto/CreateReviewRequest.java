package com.managementSystem.review.dto;

import lombok.Getter;

@Getter
public class CreateReviewRequest {
    private Long productId;
    private Long orderId;
    private Integer rating;
    private String content;

}
