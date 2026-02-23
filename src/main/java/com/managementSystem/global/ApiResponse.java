package com.managementSystem.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    String status;
    String message;
    T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Success!", null, data);
    }

    public static <T> ApiResponse<T> successWithNoContent() {
        return new ApiResponse<>("Success!", null, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("Error!", message, null);
    }

}
