package com.managementSystem.exception;


import lombok.Getter;

@Getter
public class CustomerException extends RuntimeException {


    private final ErrorCode errorCode;

    public CustomerException (ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
