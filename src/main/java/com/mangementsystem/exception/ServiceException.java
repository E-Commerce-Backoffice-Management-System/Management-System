package com.mangementsystem.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException{
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ServiceException(HttpStatus httpStatus, String message, ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
