package com.managementSystem.admin.dto;

import com.managementSystem.admin.entity.AdminRole;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AdminSignupRequest {
    @NotNull(message = "이름은 필수입니다.")
    private String name;

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상입니다.")
    private String password;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

    @NotNull(message = "역할은 필수입니다.")
    private AdminRole role;
}
