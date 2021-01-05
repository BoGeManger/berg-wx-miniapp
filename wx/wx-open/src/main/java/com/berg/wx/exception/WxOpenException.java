package com.berg.wx.exception;

import lombok.Data;

@Data
public class WxOpenException extends RuntimeException {

    String errorMsg;

    public WxOpenException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
}
