package com.berg.exception;

import com.berg.message.MessageConstant;

public class UserFriendException extends AppException {
    public UserFriendException(String errorMsg) {
        super(MessageConstant.USER_FRIENDLY_ERROR_CODE,errorMsg);
    }
}
