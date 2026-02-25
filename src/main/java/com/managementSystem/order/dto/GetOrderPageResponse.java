package com.managementSystem.order.dto;

import org.springframework.data.domain.Page;
import java.util.List;

public record GetOrderPageResponse(
        List<GetOrderListResponse> orders,
        long totalElements,
        int totalPages,
        int currentPage,
        int size
) {
    //Page 객체를 dto로 바꿔줌
    public static GetOrderPageResponse from(Page< GetOrderListResponse > page) {
        return new GetOrderPageResponse(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber()+1,
                page.getSize()
        );
    }
}
