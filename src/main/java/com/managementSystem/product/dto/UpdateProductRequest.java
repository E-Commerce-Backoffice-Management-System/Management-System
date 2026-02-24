package com.managementSystem.product.dto;

import com.managementSystem.product.enums.Category;
import jakarta.validation.constraints.*;

public record UpdateProductRequest (

    @NotEmpty(message = "상품명은 필수입니다.")
    @Size(min = 1, message = "상품명은 1자 이상이어야 합니다.")
    String name,

    @NotNull(message = "카테고리는 필수입니다.")
//    @NotBlank(message = "카테고리는 빈칸, 띄어쓰기 불가합니다.")
    Category category,

    @NotNull(message = "카테고리는 필수입니다.")
    @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
    Long price
) {}
