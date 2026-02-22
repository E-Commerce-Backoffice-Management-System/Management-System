package com.mangementsystem.order.dto;

import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.order.entity.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderResponse {
    private final String orderId;
    private final String productName;
    private final String category;
    private final int price;
    private final int quantity;
    private final String orderStatus;
    private final String customerName;
    private final String customerEmail;
    private final LocalDateTime orderDate;

    //cs 주문시 관리자 정보
    private final String adminName;
    private final String adminEmail;
    private final String adminRole;

    public static OrderResponse from(Order order) {
        // admin null 체크
        Admin admin = order.getAdmin();

        String adminName = (admin != null) ? admin.getName() : null;
        String adminEmail = (admin != null) ? admin.getEmail() : null;
        String adminRole = (admin != null && admin.getRole() != null)
                ? admin.getRole().getTitle()
                : null;

        return new OrderResponse(
                order.getId().toString(),
                order.getProductName(),
                order.getProduct().getCategory().getCategory(),
                (int) (order.getQuantity() * order.getTotalPrice()),
                order.getQuantity(),
                order.getStatus().getStatus(),
                order.getCustomerName(),
                order.getCustomer().getEmail(),
                order.getCreatedAt(),
                adminName,
                adminEmail,
                adminRole
        );
    }
}