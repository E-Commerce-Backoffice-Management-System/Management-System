package com.managementSystem.admin.dto;

import com.managementSystem.admin.entity.AdminStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminStatusRequest {
    @NotNull(message = "승인/거부 상태 값은 필수 입니다.")
    private AdminStatus status; // ACTIVE 또는 REJECTED
    private String rejectReason; // 거부일 때만 필수
}
