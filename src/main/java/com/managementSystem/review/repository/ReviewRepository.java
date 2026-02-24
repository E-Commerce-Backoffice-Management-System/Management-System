package com.managementSystem.review.repository;

import com.managementSystem.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r " +
            "JOIN FETCH r.customer c " +
            "JOIN FETCH r.product p " +
            "WHERE (:rating IS NULL OR r.rating = :rating) " +
            "AND (:keyword IS NULL OR c.name LIKE %:keyword% OR p.name LIKE %:keyword%)")
    Page<Review> searchReviews(@Param("rating") Integer rating,
                               @Param("keyword") String keyword,
                               Pageable pageable);
}
