package com.mangementsystem.product.entity;

import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.global.BaseEntity;
import com.mangementsystem.product.enums.Category;
import com.mangementsystem.product.enums.Status;
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

    public Product(String productName, Category category, int price, int stock, Status status, Admin admin) {
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
            this.status = Status.SOLDOUT;
        } else {
            this.stock = stock;
            this.status = Status.ONSALE;
        }
    }
    public void updateProductStatus(Status status) {
        this.status = status;
    }
}
