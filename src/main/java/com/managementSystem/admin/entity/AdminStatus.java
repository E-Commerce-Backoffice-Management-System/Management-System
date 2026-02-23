package com.managementSystem.admin.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminStatus {
    PENDING("PENDING", "승인 대기"),
    ACTIVE("ACTIVE", "활성화"),
    REJECTED("REJECTED", "거부"),
    SUSPENDED("SUSPENDED", "일시 정지"),
    INACTIVE("INACTIVE", "비활성화");

    private final String key;
    private final String value;
}
