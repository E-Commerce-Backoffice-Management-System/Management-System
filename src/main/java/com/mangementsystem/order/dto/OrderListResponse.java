package com.mangementsystem.order.dto;

import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.order.entity.Order;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderListResponse {
    private final Long Id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final Long totalPrice;
    private final String orderStatus;
    private final LocalDateTime orderDate;
    //cs 주문시 관리자 이름
    private final String adminName;

    public static OrderListResponse from(Order order) {
        Admin admin = order.getAdmin();

        String name = (admin != null) ? admin.getName() : null;

        return new OrderListResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getCustomerName(),
                order.getProductName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus().getStatus(),
                order.getOrderDate(),
                name
        );
    }
}
