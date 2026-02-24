package com.managementSystem.product.dto;

import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

public record UpdateProductResponse(
        Long id,
        String name,
        Category category,
        Long price,
        int stock,
        Status status,
        LocalDateTime createdAt
) {
    public static UpdateProductResponse from(Product product) {
        return new UpdateProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt()
        );
    }
}