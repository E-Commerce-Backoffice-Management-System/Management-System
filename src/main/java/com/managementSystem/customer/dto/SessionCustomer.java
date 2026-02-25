package com.managementSystem.customer.dto;

import lombok.Getter;

@Getter
public class SessionCustomer {
    private final Long id;
    private final String email;

    public SessionCustomer(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
