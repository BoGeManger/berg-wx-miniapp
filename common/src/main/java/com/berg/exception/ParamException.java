package com.berg.exception;

import com.berg.message.MessageConstant;

public class ParamException extends AppException{
    public ParamException(String errorMsg) {
        super(MessageConstant.PARAMETER_ERROR_CODE, errorMsg);
    }
}
