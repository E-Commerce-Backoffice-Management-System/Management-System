package com.managementSystem.exception;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp; // 에러가 발생한 시간
    private final int status; // 에러 상태 코드
    private final String code; // 명세서 코드
    private final String error; // 에러 이름
    private final String message; // 에러 메시지
    private final String path; // 에러가 발생한 경로

    public ErrorResponse(LocalDateTime timestamp, int status, String code, String error, String message, String path){
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
