package com.managementSystem.order.dto;

import com.managementSystem.order.entity.Order;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class CreateOrderResponse {
    private final Long id;
    private final String orderNumber;
    private final String customerName;
    private final String productName;
    private final int quantity;
    private final Long totalPrice;
    private final LocalDate orderDate;
    private final String status;

    public CreateOrderResponse(Order order) {
        this.id = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.customerName = order.getCustomer().getName();
        this.productName = order.getProduct().getName(); // Product 엔티티 필드명 확인 필요
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderDate = order.getOrderDate();
        this.status = order.getStatus().name();
    }
}