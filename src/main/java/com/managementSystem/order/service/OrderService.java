package com.managementSystem.order.service;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.customer.repository.CustomerRepository;
import com.managementSystem.order.dto.*;
import com.managementSystem.order.entity.Order;
import com.managementSystem.order.entity.OrderStatus;
import com.managementSystem.order.repository.OrderRepository;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request, Admin loginAdmin) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        if (product.getStatus() == Status.DISCONTINUED) {
            throw new IllegalArgumentException("단종된 상품입니다.");
        }
        if (product.getStatus() == Status.SOLD_OUT) {
            throw new IllegalArgumentException("품절된 상품입니다.");
        }

        Order order = new Order(customer, product, loginAdmin, request.getQuantity());
        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public Page<GetOrderResponse> getOrderList(String keyword, OrderStatus status, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return orderRepository.findAllByStatus(status, pageable).map(GetOrderResponse::from);
        } else if (keyword.startsWith("ORD")) {
            return orderRepository.findAllByOrderNumberContainingAndStatus(keyword, status, pageable).map(GetOrderResponse::from);
        } else {
            return orderRepository.findAllByCustomerNameContainingAndStatus(keyword, status, pageable).map(GetOrderResponse::from);
        }
    }
}