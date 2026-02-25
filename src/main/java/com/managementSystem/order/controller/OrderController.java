package com.managementSystem.order.controller;

import com.managementSystem.admin.dto.SessionAdmin;
import com.managementSystem.customer.dto.SessionCustomer;
import com.managementSystem.global.dto.ApiResponse;
import com.managementSystem.order.dto.*;
import com.managementSystem.order.entity.OrderStatus;
import com.managementSystem.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성 (CS 주문)
    @PostMapping("/admins/orders")
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createAdminOrder(
            @RequestBody CreateOrderRequest request,
            @SessionAttribute(name = "sessionAdmin", required = false) SessionAdmin sessionAdmin
    ) {
        if (sessionAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CreateOrderResponse response = orderService.createAdminOrder(request, sessionAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    // 주문 생성 (고객 직접 주문)
    @PostMapping("/customers/orders")
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createCustomerOrder(
            @RequestBody CreateOrderRequest request,
            @SessionAttribute(name = "sessionCustomer", required = false) SessionCustomer sessionCustomer
    ) {
        if (sessionCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        CreateOrderResponse response = orderService.createCustomerOrder(request, sessionCustomer.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    // [관리자 전용] 주문 리스트 조회 (페이징/키워드검색)
    @GetMapping("/admins/orders")
    public ResponseEntity<ApiResponse<GetOrderPageResponse>> getOrderList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sort,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin
    ) {
        if (loginAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        GetOrderPageResponse response = orderService.getOrderList(keyword, status, page, size, sortBy, sort);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    // 주문 상세 정보 조회 (고객)
    @GetMapping("/customers/orders/{orderId}")
    public ResponseEntity<ApiResponse<GetOrderDetailResponse>> getOrderDetail(
            @PathVariable Long orderId,
            @SessionAttribute(name = "sessionCustomer", required = false) SessionCustomer sessionCustomer
    ) {
        if (sessionCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        GetOrderDetailResponse response = orderService.getOrderDetail(orderId, sessionCustomer.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    // 주문 상세 정보 조회 (CS관리자)
    @GetMapping("/admins/orders/{orderId}")
    public ResponseEntity<ApiResponse<GetOrderDetailResponse>> getOrderDetailAdmin(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin
    ) {
        if (loginAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        GetOrderDetailResponse response = orderService.getOrderDetailAdmin(orderId, loginAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    // 주문 취소
    @PatchMapping("/orders/{id}/cancel")
    public ResponseEntity<ApiResponse<CancelOrderResponse>> cancelOrder(
            @PathVariable Long id,
            @Valid @RequestBody CancelOrderRequest request
    ) {
        CancelOrderResponse response = orderService.cancelOrder(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }
}