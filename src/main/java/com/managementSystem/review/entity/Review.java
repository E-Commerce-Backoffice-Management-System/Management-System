package com.managementSystem.review.entity;

import com.managementSystem.customer.entity.Customer;
import com.managementSystem.global.BaseEntity;
import com.managementSystem.order.entity.Order;
import com.managementSystem.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private Integer rating;

    @Column(length = 100, nullable = false)
    private String content;

    private boolean isDeleted = false;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "admin_id", nullable = false)
//    private Admin admin;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Review(Integer rating, String content, Product product, Customer customer, Order order) {
        this.rating = rating;
        this.content = content;
        this.product = product;
        this.customer = customer;
        this.order = order;
    }

    public Review(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public void delete(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
