package com.managementSystem.admin.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminApproveRequest {
    @NotNull(message = "승인할 관리자 ID는 필수 입니다.")
    private Long adminId;
}
