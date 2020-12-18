package com.berg.common.exception;

import com.berg.common.constant.MessageConstants;

public class FailException extends AppException{
    public FailException(String errorMsg) {
        super(MessageConstants.SYSTEM_ERROR_CODE, errorMsg);
    }
}
