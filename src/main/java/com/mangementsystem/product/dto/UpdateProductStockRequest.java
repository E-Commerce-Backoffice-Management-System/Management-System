package com.mangementsystem.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateProductStockRequest {

    @NotBlank(message = "재고는 필수입니다.")
    @Min(value = 0, message = "재고는 0개 이상이어야 합니다.")
    private int stock;
}
