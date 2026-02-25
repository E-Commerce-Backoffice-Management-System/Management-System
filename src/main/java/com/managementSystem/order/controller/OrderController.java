package com.managementSystem.order.controller;

import com.managementSystem.admin.dto.SessionAdmin;
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
    ){
        if(sessionAdmin == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // 서비스 메서드명을 createAdminOrder로 호출해야 합니다.
        CreateOrderResponse response = orderService.createAdminOrder(request, sessionAdmin);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    // 주문 생성 (고객 직접 주문)
    @PostMapping("/customers/orders")
    public ResponseEntity<ApiResponse<CreateOrderResponse>> createCustomerOrder(
            @RequestBody CreateOrderRequest request,
            HttpSession session
    ){
        // 세션에서 로그인한 고객 ID 추출
        Long loginCustomerId = (Long) session.getAttribute("loginCustomer");

        if (loginCustomerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 서비스 호출 시 세션 ID 전달
        CreateOrderResponse response = orderService.createCustomerOrder(request, loginCustomerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
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
    ){
        if(loginAdmin == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        GetOrderPageResponse response = orderService.getOrderList(keyword, status, page, size, sortBy, sort);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    // 주문 상세 정보 조회 (고객)
    @GetMapping("/customers/orders/{orderId}")
    public ResponseEntity<ApiResponse<GetOrderDetailResponse>> getOrderDetail(
            @PathVariable Long orderId,
            HttpSession session
    ){
        // 세션에서 로그인한 고객의 ID 꺼냄
        Long customerId = (Long) session.getAttribute("loginCustomer");
        if (customerId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        GetOrderDetailResponse response = orderService.getOrderDetail(orderId, customerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }

    // 주문 상세 정보 조회 (CS관리자)
    @GetMapping("/admins/orders/{orderId}")
    public ResponseEntity<ApiResponse<GetOrderDetailResponse>> getOrderDetailAdmin(
            @PathVariable Long orderId,
            @SessionAttribute(name = "loginAdmin", required = false) SessionAdmin loginAdmin
    ){
        if(loginAdmin == null){
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

//    // 주문 취소
//    @PatchMapping("/orders/{id}/camcel")
//    public ResponseEntity<ApiResponse<CancelOrderResponse>> cancelOrder(
//            @PathVariable Long id,
//            @Valid @RequestBody CancelOrderRequest request
//    ) {
//        return ResponseEntity.status(HttpStatus.OK).body(
//                ApiResponse.success(orderService.cancelOrder(id,request)));
//    }
}