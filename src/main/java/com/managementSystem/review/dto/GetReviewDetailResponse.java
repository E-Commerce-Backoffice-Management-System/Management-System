package com.managementSystem.review.dto;

import com.managementSystem.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetReviewDetailResponse {

    private final String productName;
    private final String customerName;
    private final String customerEmail;
    private final LocalDateTime createdAt;
    private final Integer rating;
    private final String content;

    public GetReviewDetailResponse(String productName, String customerName, String customerEmail,
                                   LocalDateTime createdAt, Integer rating, String content) {
        this.productName = productName;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.createdAt = createdAt;
        this.rating = rating;
        this.content = content;
    }
}
