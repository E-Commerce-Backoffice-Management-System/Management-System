package com.mangementsystem.admin.dto;

import lombok.Getter;

@Getter
public class AdminUpdateProfileResponse {

    private final String name;
    private final String email;
    private final String phoneNumber;

    public AdminUpdateProfileResponse(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
