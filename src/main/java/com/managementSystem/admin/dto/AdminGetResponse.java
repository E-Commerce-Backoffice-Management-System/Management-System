package com.managementSystem.admin.dto;

import com.managementSystem.admin.entity.AdminRole;
import com.managementSystem.admin.entity.AdminStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminGetResponse {
    private final Long id;
    private final String name; // 발제 내용 이름 추가
    private final String email;
    private final String phoneNumber;
    private final AdminRole role;
    private final AdminStatus status;
    private final LocalDateTime createdAt;
    private final LocalDateTime approvedAt;


    public AdminGetResponse(Long id, String name, String email, String phoneNumber, AdminRole role,
                            AdminStatus status, LocalDateTime createdAt, LocalDateTime approvedAt) {
        this.id = id;
        this.name = name; // 발제 내용 이름 추가
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.approvedAt = approvedAt;
    }
}
