package com.mangementsystem.exception;

import lombok.Getter;

@Getter
public class FormMistakeException extends RuntimeException {

    private final ErrorCode errorCode;

    public FormMistakeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
