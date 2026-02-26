package com.managementSystem.customer.dto;

import com.managementSystem.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
public class CustomerSignupResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final CustomerStatus status;

    public CustomerSignupResponse(Long id, String name, String email, CustomerStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

}
