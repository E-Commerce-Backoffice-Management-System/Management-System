package com.managementSystem.product.dto;

import com.managementSystem.product.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateProductRequest {

    @NotEmpty(message = "상품명은 필수입니다.")
    @Size(min = 1, message = "상품명은 1자 이상이어야 합니다.")
    private String productName;

    @NotEmpty(message = "카테고리는 필수입니다.")
    @NotBlank(message = "카테고리는 빈칸, 띄어쓰기 불가합니다.")
    private Category category;

    @NotBlank(message = "가격은 필수입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    private int price;
}
