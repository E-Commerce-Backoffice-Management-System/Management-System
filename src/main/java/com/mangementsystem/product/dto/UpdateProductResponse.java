package com.mangementsystem.product.dto;

import com.mangementsystem.product.entity.Product;
import com.mangementsystem.product.enums.Category;
import com.mangementsystem.product.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateProductResponse {

    private final Long id;
    private final String productName;
    private final Category category;
    private final int stock;
    private final Status status;
    private final LocalDateTime updatedAt;
    private final String createdBy;
    private final String createdByEmail;

    public UpdateProductResponse(Product product) {
        this.id = product.getId();
        this.productName = product.getProductName();
        this.category = product.getCategory();
        this.stock = product.getStock();
        this.status = product.getStatus();
        this.updatedAt = product.getUpdatedAt();
        this.createdBy = product.getAdmin().getName();
        this.createdByEmail = product.getAdmin().getEmail();
    }
}
