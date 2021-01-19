package com.berg.common.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {

    String errorCode;
    String errorMsg;

    public AppException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
