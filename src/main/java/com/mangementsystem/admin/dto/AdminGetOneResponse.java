package com.mangementsystem.admin.dto;

import com.mangementsystem.admin.entity.AdminRole;
import com.mangementsystem.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminGetOneResponse {
    private final Long id;
    private final String email;
    private final String phoneNumber;
    private final AdminRole role;
    private final AdminStatus adminStatus;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public AdminGetOneResponse(Long id, String email, String phoneNumber, AdminRole role, AdminStatus adminStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.adminStatus = adminStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
