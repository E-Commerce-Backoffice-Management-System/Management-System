package com.managementSystem.customer.dto;

import com.managementSystem.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCustomerResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final String status;
    private final LocalDateTime createdAt;
    private final long totalOrderCount;
    private final long totalOrderAmount;

    public GetCustomerResponse(Long id, String name, String email, String phoneNumber, CustomerStatus status, LocalDateTime createdAt, long totalOrderCount, long totalOrderAmount) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.status = status.getDescription();
        this.createdAt = createdAt;
        this.totalOrderCount = totalOrderCount;
        this.totalOrderAmount = totalOrderAmount;
    }
}
