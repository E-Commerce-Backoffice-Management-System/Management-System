package com.managementSystem.customer.dto;

import lombok.Getter;

@Getter
public class CustomerSignupRequest {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
}
