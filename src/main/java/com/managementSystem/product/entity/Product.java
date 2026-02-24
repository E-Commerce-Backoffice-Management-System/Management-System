package com.managementSystem.product.entity;


import com.managementSystem.admin.entity.Admin;
import com.managementSystem.exception.ErrorCode;
import com.managementSystem.exception.ProductException;
import com.managementSystem.global.BaseEntity;
import com.managementSystem.product.enums.Category;
import com.managementSystem.product.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    // 상품 기본 정보
    @Column(nullable = false, length = 120)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Category category;

    // 가격(원 단위) - 필요하면 BigDecimal로 바꿔도 됨
    @Column(nullable = false)
    private Long price;

    // 재고
    @Column(nullable = false)
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Status status;

    // 등록 관리자명(단방향)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "adminId", nullable = false)
    private Admin createdBy;

    private boolean deleted;

    @Builder
    public Product(String name, Category category, Long price, int stock, Status status, Admin createdBy) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.updateStock(stock);
        this.status = status;
        this.createdBy = createdBy;
    }

    //상품 정보 수정
    public void updateProduct(String name, Category category, Long price){
        this.name = name;
        this.category = category;
        this.price = price;
    }

    //재고 변경 및 상태 자동 갱신
    public void updateStock(int newStock) {
        if (this.status == Status.DISCONTINUED) {
            throw new ProductException(ErrorCode.PRODUCT_DISCONTINUED);
        }
        if (newStock < 0) {
            throw new ProductException(ErrorCode.INVALID_STOCK_QUANTITY);
        }
        this.stock = newStock;
        updateStatusByStock();
    }
    //재고 증가
    public void increaseStock(int quantity) {
        if (this.status == Status.DISCONTINUED) {
            throw new ProductException(ErrorCode.PRODUCT_DISCONTINUED);
        }
        if (quantity <= 0) {
            throw new ProductException(ErrorCode.INVALID_STOCK_QUANTITY);
        }
        this.stock += quantity;
        updateStatusByStock();
    }
    //재고 감소
    public void decreaseStock(int quantity) {
        if (this.status == Status.DISCONTINUED) {
            throw new ProductException(ErrorCode.PRODUCT_DISCONTINUED);
        }
        // 감소요청이 0보다 작거나 같을 경우 예외처리(증감수량은 1이상이어야해)
        if (quantity <= 0) {
            throw new ProductException(ErrorCode.INVALID_STOCK_QUANTITY);
        }
        // 재고보다 감소 요청이 더 많을 경우 예외처리
        if (this.stock - quantity < 0) {
            throw new ProductException(ErrorCode.INSUFFICIENT_STOCK);
        }
        this.stock -= quantity;
        updateStatusByStock();
    }
    //재고에 따른 상품 상태 변화
    public void updateStatusByStock() {

        if (this.status == Status.DISCONTINUED) {
            return;
        }

        if (this.stock <= 0) {
            this.status = Status.SOLD_OUT;
        } else {
            this.status = Status.ON_SALE;
        }
    }
}