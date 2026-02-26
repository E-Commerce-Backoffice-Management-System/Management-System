package com.managementSystem.order.repository;

import com.managementSystem.order.dto.GetOrderListResponse;
import com.managementSystem.order.entity.OrderStatus;
import com.managementSystem.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT new com.managementSystem.order.dto.GetOrderListResponse(
            o.id, 
            o.orderNumber, 
            c.name, 
            p.name, 
            o.quantity, 
            o.totalPrice, 
            o.orderDate, 
            o.status,
            COALESCE(a.name, '고객직접주문')
        )
        FROM Order o
        JOIN o.customer c
        JOIN o.product p
        LEFT JOIN o.admin a
        WHERE (:keyword IS NULL OR o.orderNumber LIKE %:keyword% OR c.name LIKE %:keyword%)
        AND (:status IS NULL OR o.status = :status)
    """)
    Page<GetOrderListResponse> findAllOrders(
            @Param("keyword") String keyword,
            @Param("status") OrderStatus status,
            Pageable pageable
    );

    // 특정 고객의 총 주문 수 조회
    long countByCustomerId(Long customerId);

    // 특정 고객의 총 구매 금액 합계 조회 (null일 경우 0 반환)
    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.customer.id = :customerId")
    long sumTotalPriceByCustomerId(@Param("customerId") Long customerId);
}