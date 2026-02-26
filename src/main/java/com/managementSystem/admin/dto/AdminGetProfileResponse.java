package com.managementSystem.admin.dto;

import lombok.Getter;

@Getter
public class AdminGetProfileResponse {
    private final String name;
    private final String email;
    private final String phoneNumber;

    public AdminGetProfileResponse(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
