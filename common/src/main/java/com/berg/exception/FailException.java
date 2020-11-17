package com.berg.exception;

import com.berg.message.MessageConstant;

public class FailException extends AppException{
    public FailException(String errorMsg) {
        super(MessageConstant.SYSTEM_ERROR_CODE, errorMsg);
    }
}
