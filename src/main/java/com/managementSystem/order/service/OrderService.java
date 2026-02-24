package com.managementSystem.order.service;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.customer.repository.CustomerRepository;
import com.managementSystem.order.dto.CreateOrderRequest;
import com.managementSystem.order.dto.CreateOrderResponse;
import com.managementSystem.order.dto.GetOrderDetailResponse;
import com.managementSystem.order.dto.GetOrderListResponse;
import com.managementSystem.order.entity.Order;
import com.managementSystem.order.entity.OrderStatus;
import com.managementSystem.order.repository.OrderRepository;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    // 주문 생성 (CS 주문 or 고객 직접 주문)
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request, Admin loginAdmin) {
        // 최소 주문 수량 검증
        if (request.getQuantity() < 1){
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
        // 관련 고객 및 상품 존재 여부 확인
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        // 상품 판매 가능 상태 확인 (단종 및 품절 여부)
        if (product.getStatus() == Status.DISCONTINUED){
            throw new IllegalArgumentException("단종된 상품입니다");
        }
        if (product.getStatus() == Status.SOLD_OUT){
            throw new IllegalArgumentException("품절되었거나 재고가 부족합니다.");
        }
        // 주문 생성 및 저장 (상품 내부 로직에서 재고 차감 발생)
        Order order = new Order(customer, product, loginAdmin, request.getQuantity());
        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public Page<GetOrderListResponse> getOrderList(String keyword, OrderStatus status, Pageable pageable) {
        Page<Order> orderPage;

        if (keyword == null || keyword.isBlank()) {
            orderPage = orderRepository.findAllByStatus(status, pageable);
        } else if (keyword.startsWith("ORD")) {
            orderPage = orderRepository.findAllByOrderNumberContainingAndStatus(keyword, status, pageable);
        } else {
            orderPage = orderRepository.findAllByCustomerNameContainingAndStatus(keyword, status, pageable);
        }
        return orderPage.map(GetOrderListResponse::from);
    }
    @Transactional(readOnly = true)
    public GetOrderDetailResponse getOrderDetail(Long orderId) {
        //존재하지 않는 ID 요청 시 에러 반환
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 주문 ID입니다: " + orderId)
        );
        return GetOrderDetailResponse.from(order);
    }
}