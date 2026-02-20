package com.mangementsystem.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class CreateCustomerRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String name;
    @NotBlank(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "010-XXXX-XXXX 형식을 맞춰주세요.")
    private String phoneNumber;
}
