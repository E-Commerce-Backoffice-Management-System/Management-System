package com.mangementsystem.order.service;

import com.mangementsystem.admin.entity.Admin;
import com.mangementsystem.customer.entity.Customer;
import com.mangementsystem.customer.repository.CustomerRepository;
//import com.mangementsystem.exception.customer.CustomerNotFoundException;
//import com.mangementsystem.exception.product.ProductNotFoundException;
import com.mangementsystem.order.dto.OrderListResponse;
import com.mangementsystem.order.dto.OrderRequest;
import com.mangementsystem.order.dto.OrderResponse;
import com.mangementsystem.order.entity.Order;
import com.mangementsystem.order.enums.OrderStatus;
import com.mangementsystem.order.repository.OrderRepository;
import com.mangementsystem.product.entity.Product;
import com.mangementsystem.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

//    public Product checkProductStock(Long productId , int stock) {
//
//        return productRepository.findById(productId).orElseThrow(
//                () ->  new ProductNotFoundException("존재하지 않는 상품입니다.")
//        );
//    }
//    public Customer checkValidateCustomer(Long customerId) throws CustomerNotFoundException {
//        Customer customer = customerRepository.findById(customerId).orElseThrow(
//                () -> new CustomerNotFoundException("존재하지 않는 유저입니다.")
//        );
//        return customer;
//
//    }

    public void manageProductStock(Long productId , int quantity) {}


    @Transactional
    public Page<OrderListResponse> getOrders(
            String keyword,
            OrderStatus status,
            int page,
            int size,
            String sortBy,
            String direction
    ) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        return orderRepository.searchOrders(keyword, status, pageable);
    }

//    @Transactional
//    public OrderResponse createOrder(OrderRequest request) {
//        Admin admin = null;
//        Customer customer = checkValidateCustomer(request.getCustomerId());
//        Product product = checkProductStock(request.getProductId(),request.getQuantity());
//        if (request.getAdminId() != null) {
//            admin = checkValidateAdmin(request.getAdminId());
//        }
//        Order order = new Order(
//                product.getPrice(),
//                request.getQuantity(),
//                OrderStatus.PREPARING,
//                product.getProductName(),
//                customer.getName(),
//                product,
//                customer,
//                admin
//        );
//        orderRepository.save(order);
//        manageProductStock(request.getProductId(),request.getQuantity());
//
//        return OrderResponse.from(order);
//    }
}
