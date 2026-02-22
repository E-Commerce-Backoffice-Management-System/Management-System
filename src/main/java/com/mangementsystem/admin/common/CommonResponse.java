package com.mangementsystem.admin.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonResponse <T>{
    private final HttpStatus status;
    private final String message;
    private final T data;

    public CommonResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponse<T> success(HttpStatus status, String message, T data) {
        return new CommonResponse<>(status, message, data);
    }
    public static <T> CommonResponse<T> success(HttpStatus status, String message) {
        return new CommonResponse<>(status, message, null);
    }
    public static <T> CommonResponse<T> fail(HttpStatus status, String message) {
        return new CommonResponse<>(status, message, null);
    }
}
