package com.mangementsystem.admin.dto;

import lombok.Getter;

@Getter
public class AdminUpdatePasswordResponse {
    private final String name;


    public AdminUpdatePasswordResponse(String name) {
        this.name = name;
    }
}
