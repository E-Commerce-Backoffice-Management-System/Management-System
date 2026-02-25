package com.managementSystem.order.service;

import com.managementSystem.admin.dto.SessionAdmin;
import com.managementSystem.admin.entity.Admin;
import com.managementSystem.admin.entity.AdminStatus;
import com.managementSystem.admin.repository.AdminRepository;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.customer.repository.CustomerRepository;
import com.managementSystem.exception.AdminException;
import com.managementSystem.exception.ErrorCode;
import com.managementSystem.exception.OrderException;
import com.managementSystem.order.dto.*;
import com.managementSystem.order.entity.Order;
import com.managementSystem.order.entity.OrderStatus;
import com.managementSystem.order.repository.OrderRepository;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final AdminRepository adminRepository;

    // 주문 생성 (CS 주문)
    @Transactional
    public CreateOrderResponse createAdminOrder(CreateOrderRequest request, SessionAdmin sessionAdmin) {
        // 1. 관리자 조회 및 권한 체크
        Admin admin = adminRepository.findById(sessionAdmin.getId())
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_USER_NOT_FOUND));

        if (admin.getStatus() != AdminStatus.ACTIVE) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }

        // 2. 공통 로직 호출 (new 키워드 제거 및 중괄호 수정)
        return processOrder(request, admin, request.getCustomerId());
    }

    // 주문 생성 (고객 직접 주문)
    @Transactional
    public CreateOrderResponse createCustomerOrder(CreateOrderRequest request, Long currentCustomerId) {
        // 고객 본인 세션 ID를 사용하여 공통 로직 호출
        return processOrder(request, null, currentCustomerId);
    }

    // 주문 처리 공통 로직 (Private)
    private CreateOrderResponse processOrder(CreateOrderRequest request, Admin admin, Long customerId) {
        // 수량 검증 (1개 미만일 경우 예외 발생)
        if (request.getQuantity() < 1) {
            throw new OrderException(ErrorCode.ORDER_INVALID_QUANTITY);
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (product.getStatus() == Status.DISCONTINUED) {
            throw new IllegalArgumentException("단종된 상품입니다.");
        }
        if (product.getStatus() == Status.SOLD_OUT) {
            throw new IllegalArgumentException("품절되었거나 재고가 부족합니다.");
        }

        // 주문 생성 및 저장
        Order order = new Order(customer, product, admin, request.getQuantity());
        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponse(savedOrder);
    }

    // 주문 리스트 조회 (관리자용 페이징)
    @Transactional(readOnly = true)
    public GetOrderPageResponse getOrderList(
            String keyword, OrderStatus status, int page, int size, String sortBy, String sort
    ){
        Sort.Direction direction = "asc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));
        Page<GetOrderListResponse> orderPage = orderRepository.findAllOrders(keyword, status, pageable);
        return GetOrderPageResponse.from(orderPage);
    }

    // 주문 상세 정보 조회 (고객용)
    @Transactional(readOnly = true)
    public GetOrderDetailResponse getOrderDetail(Long orderId, Long currentCustomerId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderException(ErrorCode.ORDER_NOT_FOUND)
        );

        // 본인 주문인지 검증
        if (!order.getCustomer().getId().equals(currentCustomerId)) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }

        return GetOrderDetailResponse.from(order);
    }

    // 주문 상세 정보 조회 (CS관리자용)
    @Transactional(readOnly = true)
    public GetOrderDetailResponse getOrderDetailAdmin(Long orderId, SessionAdmin sessionAdmin) {
        Admin admin = adminRepository.findById(sessionAdmin.getId())
                .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_NO_AUTHORITY));

        if (admin.getStatus() != AdminStatus.ACTIVE) {
            throw new AdminException(ErrorCode.ADMIN_NO_AUTHORITY);
        }

        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

        return GetOrderDetailResponse.from(order);
    }

    // 주문 취소
    @Transactional
    public CancelOrderResponse cancelOrder(Long id, CancelOrderRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new OrderException(ErrorCode.ORDER_NOT_FOUND));

        if (order.getStatus() != OrderStatus.PREPARING) {
            throw new OrderException(ErrorCode.ORDER_NOT_PREPARING);
        }

        Product product = order.getProduct();

        if(!product.isDeleted()) {
            product.increaseStock(order.getQuantity());
            if (product.getStatus() != Status.DISCONTINUED) {
                product.updateStatusByStock();
            }
        }

        order.cancel(OrderStatus.CANCELLED, request.cancelReason());
        return CancelOrderResponse.from(order.getOrderNumber());
    }
}