package com.managementSystem.order.controller;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.global.dto.ApiResponse;
import com.managementSystem.order.dto.*;
import com.managementSystem.order.entity.OrderStatus;
import com.managementSystem.order.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/admin/orders")
    public ResponseEntity<CreateOrderResponse> createAdminOrder(
            @RequestBody CreateOrderRequest request,
            HttpSession session
    ){
        Admin loginAdmin = (Admin) session.getAttribute("loginAdmin");
        if(loginAdmin == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, loginAdmin));
    }

    @PostMapping("/customer/orders")
    public ResponseEntity<CreateOrderResponse> createCustomerOrder(
            @RequestBody CreateOrderRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, null));
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<Page<GetOrderListResponse>> getOrderList(
            @RequestParam(required = false) String keyword,
            @RequestParam OrderStatus status,
            @PageableDefault(page = 0, size = 10, sort = "orderDate", direction = Sort.Direction.DESC) Pageable pageable
    ){
        Page<GetOrderListResponse> response = orderService.getOrderList(keyword, status, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/admin/orders/{orderId}")
    public ResponseEntity<GetOrderDetailResponse> getOrderDetail(
            @PathVariable Long orderId
    ){
        GetOrderDetailResponse response = orderService.getOrderDetail(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 주문 취소
    @PatchMapping("/orders/{id}/camcel")
    public ResponseEntity<ApiResponse<CancelOrderResponse>> cancelOrder(
            @PathVariable Long id,
            @Valid @RequestBody CancelOrderRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.success(orderService.cancelOrder(id,request)));
    }
}