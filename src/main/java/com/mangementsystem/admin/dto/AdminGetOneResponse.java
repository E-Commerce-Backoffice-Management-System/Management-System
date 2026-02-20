package com.mangementsystem.admin.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminGetOneResponse {
    private final Long id;
    private final String email;
    private final String phoneNumber;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public AdminGetOneResponse(Long id, String email, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
