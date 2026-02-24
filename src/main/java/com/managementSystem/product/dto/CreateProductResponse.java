package com.managementSystem.product.dto;

import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreateProductResponse(
        Long id,
        String name,
        Category category,
        Long price,
        int stock,
        Status status,
        LocalDateTime createdAt
) {
    public static CreateProductResponse from(Product product){
        return CreateProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .category(product.getCategory())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.getStatus())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
