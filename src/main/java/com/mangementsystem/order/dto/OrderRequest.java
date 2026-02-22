package com.mangementsystem.order.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class OrderRequest {
    @Min(value = 0 , message = "수량은 0개 이상 이어야 합니다.")
    private Integer quantity;
    @Min(1)
    private Long productId;
    @Min(1)
    private Long customerId;

    private Long adminId;
}
