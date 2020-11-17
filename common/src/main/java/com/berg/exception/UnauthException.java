package com.berg.exception;

import com.berg.message.MessageConstant;

public class UnauthException extends AppException{
    public UnauthException(String errorMsg) {
        super(MessageConstant.UNAUTH_ERROR_CODE,errorMsg);
    }
}
