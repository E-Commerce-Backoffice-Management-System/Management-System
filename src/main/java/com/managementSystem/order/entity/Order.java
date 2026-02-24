package com.managementSystem.order.entity;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String orderNumber;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = true)
    private Admin admin;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private Long totalPrice;
    @CreatedDate
    @Column(nullable = false)
    private LocalDate orderDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private String cancelReason;

    public Order(Customer customer, Product product, Admin admin, int quantity){
        validateQuantity(quantity);

        this.orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.customer = customer;
        this.product = product;
        this.admin = admin;
        this.quantity = quantity;
        this.totalPrice = (long) product.getPrice() * quantity;
        this.status = OrderStatus.PREPARING;
    }

    private void validateQuantity(int quantity){
        if(quantity < 1){
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
    }

    public void cancel(String reason){
        if (this.status != OrderStatus.PREPARING){
            throw new IllegalStateException("주문취소는 준비중 상태에서만 허용합니다.");
        }
        this.status = OrderStatus.CANCELLED;
        this.cancelReason = reason;
    }
}
