package com.mangementsystem.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class AdminLoginRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상입니다.")
    private String password;
}
