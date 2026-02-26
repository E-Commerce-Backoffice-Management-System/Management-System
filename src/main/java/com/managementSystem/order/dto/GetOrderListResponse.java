package com.managementSystem.order.dto;

import com.managementSystem.order.entity.OrderStatus;
import java.time.LocalDate;

public record GetOrderListResponse(
        Long id,
        String orderNumber,
        String customerName,
        String productName,
        int quantity,
        Long totalPrice,
        LocalDate orderDate,
        OrderStatus status,
        String adminName
) {
    public static GetOrderListResponse from(com.managementSystem.order.entity.Order order) {
        return new GetOrderListResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomer().getName(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getOrderDate(),
                order.getStatus(),
                order.getAdmin() != null ? order.getAdmin().getName() : "고객직접주문"
        );
    }
}