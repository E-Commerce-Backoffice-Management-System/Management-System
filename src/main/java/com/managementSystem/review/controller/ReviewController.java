package com.managementSystem.review.controller;

import com.managementSystem.global.dto.ApiResponse;
import com.managementSystem.review.dto.CreateReviewRequest;
import com.managementSystem.review.dto.CreateReviewResponse;
import com.managementSystem.review.dto.GetReviewDetailResponse;
import com.managementSystem.review.dto.GetReviewResponse;
import com.managementSystem.review.sevice.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 생성
    @PostMapping("/customers/{customerId}/products/{productId}/reviews")
    // Session 로그인 시스템에서 userId나 user 정보를 받아서 매핑
    public ResponseEntity<ApiResponse<CreateReviewResponse>> createReview(
            @PathVariable Long customerId,
            @PathVariable Long productId, // 상품에 종속되는 것이 (customerId , productId)
            @RequestBody CreateReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(reviewService.save(customerId, productId, request)));
    }

    // 리뷰 리스트 조회
    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<Page<GetReviewResponse>>> getReviews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer rating,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reviewService.getReviews(page, size, keyword, rating, sortBy, direction)));

    }

    // 리뷰 단건 조회
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse<GetReviewDetailResponse>> getReview(@PathVariable Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(reviewService.getReviewDetail(reviewId)));
    }

    // 리뷰 삭제
    @DeleteMapping("/admins/{adminId}/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long adminId,
            @PathVariable Long reviewId) {
        reviewService.deleteReview(adminId, reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
