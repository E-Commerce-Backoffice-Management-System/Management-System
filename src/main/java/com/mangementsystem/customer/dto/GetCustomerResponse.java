package com.mangementsystem.customer.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCustomerResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final LocalDateTime createaAt;

    public GetCustomerResponse(Long id, String name, String email, String phoneNumber, LocalDateTime createaAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createaAt = createaAt;
    }
}
