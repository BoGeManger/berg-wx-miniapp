package com.berg.common.exception;

import com.berg.common.constant.MessageConstants;

public class UnauthException extends AppException{
    public UnauthException(String errorMsg) {
        super(MessageConstants.UNAUTH_ERROR_CODE,errorMsg);
    }
}
