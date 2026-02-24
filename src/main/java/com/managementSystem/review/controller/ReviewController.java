package com.managementSystem.review.controller;

import com.managementSystem.global.dto.ApiResponse;
import com.managementSystem.review.dto.CreateReviewRequest;
import com.managementSystem.review.dto.CreateReviewResponse;
import com.managementSystem.review.dto.GetReviewResponse;
import com.managementSystem.review.sevice.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/customers/{customerId}/reviews")
    public ResponseEntity<ApiResponse<CreateReviewResponse>> createReview(
            @PathVariable Long customerId,
            @RequestBody CreateReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(reviewService.save(customerId, request)));
    }

    @GetMapping("/reviews")
    public ResponseEntity<ApiResponse<List<GetReviewResponse>>> getReviews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer rating,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(reviewService.getReviews()));

    }


}
