package com.managementSystem.product.entity;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.global.BaseEntity;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "adminId", nullable = false)
    private Admin admin;

    public Product(String productName, Category category, int price, int stock, Status status,Admin admin) {
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.status = status;
        this.admin = admin;
    }

    public void updateProduct(String productName, Category category, int price) {
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    public void updateProductStock(int stock) {
        this.stock = stock;
    }

    public void updateProductStockAndStatus(int stock) {
        if (this.stock <= 0) {
            this.stock = 0;
            this.status = Status.SOLD_OUT;
        } else {
            this.stock = stock;
            this.status = Status.ON_SALE;
        }
    }
    public void updateProductStatus(Status status) {
        this.status = status;
    }
}
