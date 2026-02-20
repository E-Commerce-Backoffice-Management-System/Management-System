package com.mangementsystem.exception;

import lombok.Getter;

@Getter
public class FormMistakeException extends ServiceException {

    public FormMistakeException(ErrorCode errorCode){
        super(errorCode);
    }
}
