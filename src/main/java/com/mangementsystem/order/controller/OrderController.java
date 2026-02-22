package com.mangementsystem.order.controller;

import com.mangementsystem.global.ApiResponse;
import com.mangementsystem.order.dto.OrderListResponse;
import com.mangementsystem.order.dto.OrderRequest;
import com.mangementsystem.order.dto.OrderResponse;
import com.mangementsystem.order.enums.OrderStatus;
import com.mangementsystem.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/orders")
    public Page<OrderListResponse> getOrders(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        return orderService.getOrders(keyword, status, page, size, sortBy, direction);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @RequestBody @Valid OrderRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(orderService.createOrder(request)));
    }

}
