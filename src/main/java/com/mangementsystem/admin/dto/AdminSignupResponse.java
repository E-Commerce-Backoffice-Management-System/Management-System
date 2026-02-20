package com.mangementsystem.admin.dto;

import com.mangementsystem.admin.entity.AdminRole;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminSignupResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final AdminRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public AdminSignupResponse(Long id, String name, String email, String phoneNumber, AdminRole role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
