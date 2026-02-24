package com.managementSystem.order.dto;

import lombok.Builder;

@Builder
public record CancelOrderResponse (
    String orderNumber,
    String message

) {
    public static CancelOrderResponse from(String orderNumber) {
        return CancelOrderResponse.builder()
                .orderNumber(orderNumber)
                .message(orderNumber + "주문이 취소되었습니다.")
                .build();
    }
}
