package com.managementSystem.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CancelOrderRequest (

    @NotBlank(message = "취소 사유는 필수 입력입니다.")
    String cancelReason // 취소사유
) {

}
