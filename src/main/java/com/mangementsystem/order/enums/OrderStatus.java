package com.mangementsystem.order.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PREPARING("준비중"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    CANCELLED("취소됨");

    private final String status;

    private OrderStatus(String status) {
        this.status = status;
    }
}
