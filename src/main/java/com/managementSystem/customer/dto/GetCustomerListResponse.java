package com.managementSystem.customer.dto;

import com.managementSystem.customer.entity.CustomerStatus;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class GetCustomerListResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final CustomerStatus status;
    private final LocalDateTime createdAt;
    private final CustomerOrderSummaryResponse summary;


    public GetCustomerListResponse(Long id, String name, String email, CustomerStatus status,
                                   LocalDateTime createdAt, CustomerOrderSummaryResponse summary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.createdAt = createdAt;
        this.summary = summary;
    }
}