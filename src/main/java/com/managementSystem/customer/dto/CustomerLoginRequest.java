package com.managementSystem.customer.dto;

import lombok.Getter;

@Getter
public class CustomerLoginRequest {
    private String email;
    private String password;
}
