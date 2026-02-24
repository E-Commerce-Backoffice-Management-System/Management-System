package com.managementSystem.order.dto;

import com.managementSystem.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrderResponse {
    private Long id;
    private String orderNumber;
    private String customerName;
    private String productName;
    private int quantity;
    private Long totalPrice;
    private LocalDate orderDate;
    private String status;
    private String adminName;

    public static GetOrderResponse from(Order order) {
        return GetOrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerName(order.getCustomer().getName())
                .productName(order.getProduct().getName())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .orderDate(order.getOrderDate())
                .status(order.getStatus().name())
                .adminName(order.getAdmin() != null ? order.getAdmin().getName() : "고객직접주문")
                .build();
    }
}