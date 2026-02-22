package com.mangementsystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter

public enum ErrorCode {
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    MISTAKE_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "가입되지 않은 이메일 입니다.."),
    NO_AUTHORITY(HttpStatus.FORBIDDEN, "해당 작업에 대한 권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾지 못했습니다."),
    PENDING_ADMIN(HttpStatus.FORBIDDEN, "관리자 승인 대기중입니다."),
    INACTIVE_ADMIN(HttpStatus.FORBIDDEN, "비활성화 상태의 계정입니다."),
    REJECTED_ADMIN(HttpStatus.FORBIDDEN, "관리자에게 거부된 계정입니다."),
    SUSPENDED_ADMIN(HttpStatus.FORBIDDEN, "정지된 계정입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
