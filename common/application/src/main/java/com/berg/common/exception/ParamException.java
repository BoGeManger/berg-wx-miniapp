package com.berg.common.exception;

import com.berg.common.constant.MessageConstants;

public class ParamException extends AppException{
    public ParamException(String errorMsg) {
        super(MessageConstants.PARAMETER_ERROR_CODE, errorMsg);
    }
}
