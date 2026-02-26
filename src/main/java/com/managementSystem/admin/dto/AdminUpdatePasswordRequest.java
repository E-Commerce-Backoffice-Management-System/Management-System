package com.managementSystem.admin.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminUpdatePasswordRequest {

    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상입니다.")
    private String password;
}
