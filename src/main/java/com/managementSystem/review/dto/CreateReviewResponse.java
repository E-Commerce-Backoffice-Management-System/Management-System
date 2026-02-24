package com.managementSystem.review.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateReviewResponse {
    private final Long id;
    private final Integer rating;
    private final String Content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CreateReviewResponse(Long id, Integer rating, String Content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rating = rating;
        this.Content = Content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
