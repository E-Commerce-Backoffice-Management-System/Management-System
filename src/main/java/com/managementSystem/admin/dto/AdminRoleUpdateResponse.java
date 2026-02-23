package com.managementSystem.admin.dto;

import com.managementSystem.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class AdminRoleUpdateResponse {

    private final Long id;
    private final String name;
    private final AdminRole role;

    public AdminRoleUpdateResponse(Long id, String name, AdminRole role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
