package com.managementSystem.review.sevice;

import com.managementSystem.customer.dto.CreateCustomerRequest;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.customer.repository.CustomerRepository;
import com.managementSystem.exception.CustomerException;
import com.managementSystem.exception.ErrorCode;
import com.managementSystem.exception.ReviewException;
import com.managementSystem.order.repository.OrderRepository;
import com.managementSystem.review.dto.CreateReviewRequest;
import com.managementSystem.review.dto.CreateReviewResponse;
import com.managementSystem.review.entity.Review;
import com.managementSystem.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public CreateReviewResponse save(Long customerId, CreateReviewRequest request){
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ReviewException(ErrorCode.CUSTOMER_NOT_FOUND.getMessage())
        );
        if(!customer.getId().equals(customerId)){
            throw new CustomerException(ErrorCode.NO_AUTHORITY);
        }
        Review review = new Review(request.getRating(),request.getContent());
        Review savedreview = reviewRepository.save(review);
        return new CreateReviewResponse(
                savedreview.getId(),
                savedreview.getRating(),
                savedreview.getContent(),
                savedreview.getCreatedAt(),
                savedreview.getUpdatedAt()
        );
    }
}
