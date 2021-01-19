package com.berg.common.exception;

import com.berg.common.constant.MessageConstants;

public class UserFriendException extends AppException {
    public UserFriendException(String errorMsg) {
        super(MessageConstants.USER_FRIENDLY_ERROR_CODE,errorMsg);
    }
}
