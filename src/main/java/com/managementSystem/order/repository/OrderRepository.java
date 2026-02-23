package com.managementSystem.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public class OrderRepository {
    public interface OrderRepository extends JpaRepository<Order, Long> {
        @Query("""
        SELECT new com.managementSystem.order.dto.OrderListResponse(
            o.id,
            o.orderNumber,
            c.name,
            p.productName,
            oi.quantity,
            o.totalPrice,
            o.orderDate,
            o.status,
            a.name
        )
        FROM Order o
        JOIN o.customer c
        JOIN o.orderProducts oi
        JOIN o.product p
        JOIN o.admin a
        WHERE (
            :status IS NULL OR o.status = :status
        )
        AND (
            :keyword IS NULL
            OR o.orderNumber LIKE %:keyword%
            OR c.name LIKE %:keyword%
        )
    """)
        Page<OrderListResponse> searchOrders(
                @Param("keyword") String keyword,
                @Param("status") OrderStatus status,
                Pageable pageable
        );
    }

}
