package com.managementSystem.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    PREPARING("준비중"),
    SHIPPING("배송중"),
    COMPLETED("배송완료"),
    CANCELLED("취소됨");

    private final String description;
}