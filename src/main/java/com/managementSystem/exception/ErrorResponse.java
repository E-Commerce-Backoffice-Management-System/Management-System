package com.managementSystem.exception;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;

    public ErrorResponse(LocalDateTime timestamp, int status, String message){
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }
}
