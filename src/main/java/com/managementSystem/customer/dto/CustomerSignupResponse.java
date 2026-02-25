package com.managementSystem.customer.dto;

import lombok.Getter;

@Getter
public class CustomerSignupResponse {
    private final Long id;
    private final String name;
    private final String email;

    public CustomerSignupResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}
