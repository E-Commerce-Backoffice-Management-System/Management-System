package com.managementSystem.product.dto;

import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;

import java.time.LocalDateTime;

public record GetAllProductResponse (
            Long id,
            String name,
            Category category,
            Long price,
            int stock,
            Status status,
            LocalDateTime createdAt,
            String adminName
) {
    public static GetAllProductResponse from(Product product){
        return new GetAllProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getCreatedBy().getName()
        );
    }
}
