package com.managementSystem.order.entity;

import com.managementSystem.admin.dto.SessionAdmin;
import com.managementSystem.admin.entity.Admin;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.product.entity.Product; // 이제 정상 임포트 가능!
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

    @Builder
    public Order(Customer customer, Product product, Admin admin, int quantity) {
        validateQuantity(quantity);
        product.updateStock(quantity); // 팀원이 만든 Product 메서드 호출

        this.orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.customer = customer;
        this.product = product;
        this.admin = admin;
        this.quantity = quantity;
        this.totalPrice = (long) product.getPrice() * quantity;
        this.status = OrderStatus.PREPARING;
    }

    private void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
    }
    // 주문 상태 변경 (준비중 -> 배송중 -> 배송완료)
    public void updateStatus(OrderStatus newStatus){
        // 배송 완료거나 취소된 주문은 수정 불가
        if (this.status == OrderStatus.COMPLETED && newStatus == OrderStatus.CANCELLED){
            throw new IllegalArgumentException("이미 완료되었거나 취소된 주문은 상태를 변경할 수 없습니다.");
        }
        //준비중이거나
        if (this.status == OrderStatus.PREPARING && newStatus != OrderStatus.SHIPPING){
            throw new IllegalArgumentException("준비중 상태에서는 배송중으로만 변경 가능합니다.");
        }
        if (this.status == OrderStatus.SHIPPING && newStatus != OrderStatus.COMPLETED){
            throw new IllegalArgumentException("배송중 상태에서는 배송완료로만 변경 가능합니다.");
        }
        if (newStatus == OrderStatus.CANCELLED){
            throw new IllegalArgumentException("주문 취소는 전용 기능을 이용해 주세요");
        }

        this.status = newStatus;
    }
    //주문 취소
    public void cancel(String reason) {
        //준비중 상태에서만 취소 가능
        if (this.status != OrderStatus.PREPARING) {
            throw new IllegalStateException("주문취소는 준비중 상태에서만 허용합니다.");
        }
        //취소 사유 필수 입력
        if (reason == null || reason.isBlank()){
            throw new IllegalArgumentException("취소 사유는 필수입니다.");
        }
        this.status = OrderStatus.CANCELLED;
        this.cancelReason = reason;
        //취소 상품 ->  재고 복구
        this.product.increaseStock(this.quantity);
    }
}