package com.mangementsystem.product.dto;

import com.mangementsystem.product.enums.Status;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class UpdateProductStatusRequest {

    @NotEmpty(message = "상품 상태 필수입니다.")
    private Status status;
}
