package com.mangementsystem.customer.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCustomerResponse {

    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final LocalDateTime createdAt;

    public CreateCustomerResponse(Long id, String name, String email, String phoneNumber, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }
}
