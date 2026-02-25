package com.managementSystem.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerOrderSummaryResponse {
    private long totalOrderCount;  // 총 주문 수
    private Long totalPurchaseAmount; // 총 구매 금액
}