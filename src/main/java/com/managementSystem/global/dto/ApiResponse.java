package com.managementSystem.global.dto;

import com.managementSystem.exception.ErrorResponse;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final ErrorResponse error;

    public ApiResponse(boolean success, T data, ErrorResponse error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    //  성공 응답
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    //  실패 응답
    public static ApiResponse<Void> fail(ErrorResponse error) {
        return new ApiResponse<>(false, null, error);
    }


}
