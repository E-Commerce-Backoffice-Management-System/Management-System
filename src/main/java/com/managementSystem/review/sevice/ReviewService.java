package com.managementSystem.review.sevice;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.admin.entity.AdminRole;
import com.managementSystem.admin.repository.AdminRepository;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.customer.repository.CustomerRepository;
import com.managementSystem.exception.*;
import com.managementSystem.order.entity.Order;
import com.managementSystem.order.repository.OrderRepository;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.repository.ProductRepository;
import com.managementSystem.review.dto.CreateReviewRequest;
import com.managementSystem.review.dto.CreateReviewResponse;
import com.managementSystem.review.dto.GetReviewDetailResponse;
import com.managementSystem.review.dto.GetReviewResponse;
import com.managementSystem.review.entity.Review;
import com.managementSystem.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final AdminRepository adminRepository;
    private final ProductRepository productRepository;

    // 리뷰 생성
    @Transactional
    public CreateReviewResponse save(Long customerId, Long productId, CreateReviewRequest request){
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ReviewException(ErrorCode.CUSTOMER_NOT_FOUND));
        if(!customer.getId().equals(customerId)){
            throw new CustomerException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductException(ErrorCode.PRODUCT_NOT_FOUND)
        );

        Order order = orderRepository.findById(1L).orElseThrow(
                () -> new OrderException(ErrorCode.INTERNAL_SERVER_ERROR)
        );
        Review review = new Review(request.getRating(),request.getContent(),product,customer,order);
        Review savedreview = reviewRepository.save(review);
        return new CreateReviewResponse(
                savedreview.getId(),
                savedreview.getRating(),
                savedreview.getContent(),
                savedreview.getCreatedAt(),
                savedreview.getUpdatedAt()
        );
    }

    // 리뷰 리스트 조회
    @Transactional(readOnly = true)
    public Page<GetReviewResponse> getReviews(int page, int size, String keyword, Integer rating, String sortBy, String direction){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Review> reviewPage = reviewRepository.searchReviews(rating,  keyword, pageable);

        return reviewPage.map(review -> new GetReviewResponse(
                review.getId(),
                review.getOrder().getOrderNumber(),
                review.getCustomer().getName(),
                review.getProduct().getName(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        ));
    }
    // 리뷰 상세 조회
    @Transactional(readOnly = true)
    public GetReviewDetailResponse getReviewDetail(Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewException(ErrorCode.REVIEW_NOT_FOUND));

        return new GetReviewDetailResponse(
                review.getProduct().getName(),
                review.getCustomer().getName(),
                review.getCustomer().getEmail(),
                review.getCreatedAt(),
                review.getRating(),
                review.getContent()
        );
    }

    @Transactional
    public void deleteReview(Long adminId, Long reviewId){
        Admin admin = adminRepository.findById(adminId).orElseThrow(
                () -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND)
        );
        if(admin.getRole() != AdminRole.SUPER_ADMIN) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewException(ErrorCode.REVIEW_NOT_FOUND)
        );
        if (review.isDeleted()) {
            throw new ReviewException(ErrorCode.ALREADY_DELETED);
        }

        review.delete(true);
    }
}
