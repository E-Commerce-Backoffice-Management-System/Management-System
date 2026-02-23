package com.managementSystem.order.repository;

import com.managementSystem.order.entity.Order;
import com.managementSystem.product.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 주문번호로 검색할 때
    Page<Order> findAllByOrderNumberContainingAndStatus(String orderNumber, Status status, Pageable pageable);
    // 고객명 검색
    Page<Order> findAllByCustomerNameContainingAndStatus(String customerName, Status status, Pageable pageable);

}
