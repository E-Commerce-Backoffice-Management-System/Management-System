package com.managementSystem.product.dto;

import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;


public record GetOneProductResponse(
        Long id,
        String name,
        Category category,
        Long price,
        int stock,
        Status status,
        LocalDateTime createdAt
        //등록 관리자 이메일
) {
    public static GetOneProductResponse from(Product product) {
        return new GetOneProductResponse(
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