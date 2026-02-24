package com.managementSystem.order.dto;

import com.managementSystem.order.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOrderDetailResponse {

    private Long id;
    private String orderNumber;
    private String status; // 주문 상태 (준비중, 배송중, 배송완료, 취소됨)
    private LocalDate orderDate;
    private int quantity;
    private Long totalPrice;

    //고객 정보
    private String customerName;
    private String customerEmail;

    //상품 정보
    private String productName;

    //관리자 정보 (CS 주문이 아닐 경우 = null)
    private String adminName;
    private String adminEmail;
    private String adminRole;

    public static GetOrderDetailResponse from(Order order){
        GetOrderDetailResponseBuilder builder = GetOrderDetailResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getStatus().name())
                .orderDate(order.getOrderDate())
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .customerName(order.getCustomer().getName())
                .customerEmail(order.getCustomer().getEmail())
                .productName(order.getProduct().getName());

        //관리자 정보가 있을때만 응답에 포함
        if(order.getAdmin() != null){
            builder.adminName(order.getAdmin().getName())
                    .adminEmail(order.getAdmin().getEmail())
                    .adminRole(order.getAdmin().getRole().name());
        }
        return builder.build();
    }
}
