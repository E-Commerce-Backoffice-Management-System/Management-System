package com.managementSystem.order.controller;

import com.managementSystem.admin.entity.Admin;
import com.managementSystem.order.dto.CreateOrderRequest;
import com.managementSystem.order.dto.CreateOrderResponse;
import com.managementSystem.order.service.OrderService;
import com.managementSystem.product.enums.Status;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    //주문 생성 (CS 대리 주문)
    @PostMapping("/admin/orders")
    public ResponseEntity<CreateOrderResponse> createAdminOrder(
            @RequestBody CreateOrderRequest request,
            HttpSession session
    ){
        Admin loginAdmin = (Admin) session.getAttribute("loginAdmin");
        // 관리자가 로그인이 상태가 아니면 에러 응답
        if(loginAdmin == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, loginAdmin));
    }
    //주문 생성 (고객 직접 주문)
    @PostMapping("/customer/orders")
    public ResponseEntity<CreateOrderResponse> createCustomerOrder(
            @RequestBody CreateOrderRequest request
    ){
        //고객이 직접 주문하는 거니까 관리자 정보는 null로 넘겨줌
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(request, null));
    }

}
