package com.mangementsystem.admin.dto;


import com.mangementsystem.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class AdminStatusUpdateResponse {

    private final AdminStatus status;

    public AdminStatusUpdateResponse(AdminStatus status) {
        this.status = status;
    }
}
