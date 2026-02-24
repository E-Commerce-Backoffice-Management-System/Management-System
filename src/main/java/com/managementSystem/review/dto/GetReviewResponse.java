package com.managementSystem.review.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetReviewResponse {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final Integer rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public GetReviewResponse(Long id, String orderNumber, String customerName, String productName, Integer rating,
                             String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.productName = productName;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    // 서비스 레이어에서는 레포지토리에서 조회한 Review 엔티티를 위에서 만든 DTO로 변환만 해주면 됩니다.

}
