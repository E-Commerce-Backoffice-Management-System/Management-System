package com.mangementsystem.admin.dto;

import com.mangementsystem.admin.entity.AdminRole;

public class SessionAdmin {
    private final Long id;
    private final String email;
    private final AdminRole role;

    public SessionAdmin(Long id, String email, AdminRole role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
