package com.mangementsystem.admin.dto;

import com.mangementsystem.admin.entity.AdminRole;
import lombok.Getter;

@Getter
public class AdminRoleUpdateResponse {

    private final AdminRole role;

    public AdminRoleUpdateResponse(AdminRole role) {
        this.role = role;
    }
}
