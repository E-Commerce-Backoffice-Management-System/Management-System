package com.managementSystem.order.service;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.customer.entity.Customer;
import com.managementSystem.customer.repository.CustomerRepository;
import com.managementSystem.order.dto.CreateOrderRequest;
import com.managementSystem.order.dto.CreateOrderResponse;
import com.managementSystem.order.entity.Order;
import com.managementSystem.order.repository.OrderRepository;
import com.managementSystem.product.entity.Product;
import com.managementSystem.product.enums.Status;
import com.managementSystem.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    public final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    //주문 생성
    @Transactional
    public CreateOrderResponse createOrder(CreateOrderRequest request, Admin loginAdmin) {
        // 상품 수량 확인
        if (request.getQuantity() < 1){
            throw new IllegalArgumentException("수량은 1 이상이어야 합니다.");
        }
        // 고객 조회
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 고객입니다."));
        // 상품 조회
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        // 재고 및 상태 확인
        if (product.getStatus() == Status.DISCONTINUED){
            throw new IllegalArgumentException("단종된 상품입니다");
        }
        if (product.getStatus() == Status.SOLD_OUT || product.getStock() < request.getQuantity()){
            throw new IllegalArgumentException("품절되었거나 재고가 부족합니다.");
        }
        // 재고 차감
        int remainingStock = product.getStock() - request.getQuantity();
        product.updateStatusByStock();
        // 주문 저장 (loginAdmin -> CS 대리 주문, null -> 고객 직접 주문)
        Order order = new Order(customer, product, loginAdmin, request.getQuantity());
        Order savedOrder = orderRepository.save(order);

        return new CreateOrderResponse(savedOrder);
    }

}

