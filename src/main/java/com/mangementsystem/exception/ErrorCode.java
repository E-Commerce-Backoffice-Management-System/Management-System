package com.mangementsystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public enum ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    FORMAL_MISTAKE_EMAIL(HttpStatus.BAD_REQUEST, "이메일 형식을 확인하세요."),
    FORMAL_MISTAKE_PHONE(HttpStatus.BAD_REQUEST, "전화번호가 올바르지 않습니다."),
    FORMAL_MISTAKE_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호 형식을 확인하세요.");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
