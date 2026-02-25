package com.managementSystem.customer.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class CustomerSignupRequest {
    private String name;
    @Email
    private String email;
    private String phoneNumber;
    private String password;
}
