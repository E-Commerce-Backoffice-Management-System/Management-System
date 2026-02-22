package com.mangementsystem.order.entity;

import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.customer.entity.Customer;
import com.mangementsystem.global.BaseEntity;
import com.mangementsystem.order.enums.OrderStatus;
import com.mangementsystem.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SoftDelete(columnName = "deleted")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private Long totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column(nullable = false)
    private int quantity;

    @Column
    private String cancelReason;

    @Column(nullable = false)
    private String customerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId")
    private Admin admin;

    @OneToMany(mappedBy = "order")
    private List<orderProducts> orderProducts;

    public Order(String orderNumber, OrderStatus status,String productName, Long totalPrice, LocalDateTime orderDate, int quantity,
                 String customerName, Customer customer, Product product, Admin admin) {
        this.orderNumber = orderNumber;
        this.status = status;
        this.productName = productName;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.customerName = customerName;
        this.customer = customer;
        this.product = product;
        this.admin = admin;
    }

    public Order(int price, @Min(value = 0, message = "수량은 0개 이상 이어야 합니다.") Integer quantity, OrderStatus orderStatus, String productName, String name, Product product, Customer customer, Admin admin) {
    }

    public void updateOrderStatus(OrderStatus status) {
        this.status = status;
    }

    public void updateStatusAndCancelOrder(String cancelReason) {
        this.status = OrderStatus.CANCELLED;
        this.cancelReason = cancelReason;
    }

    public void completeOrder() {
        this.status = OrderStatus.DELIVERED;
    }
}
