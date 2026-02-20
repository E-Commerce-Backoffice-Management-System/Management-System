package com.mangementsystem.admin.dto;

import com.mangementsystem.admin.entity.AdminRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AdminRoleUpdateRequest {

    @NotNull(message = "권한은 필수입니다.")
    private AdminRole role;
}
