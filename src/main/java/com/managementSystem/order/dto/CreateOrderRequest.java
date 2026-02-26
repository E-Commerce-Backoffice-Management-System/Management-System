package com.managementSystem.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateOrderRequest {

    private Long customerId; // 고객 번호
    private Long productId;  // 상품 번호
    private int quantity;    // 주문 수량
}
