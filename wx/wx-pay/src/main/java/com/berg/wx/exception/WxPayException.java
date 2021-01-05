package com.berg.wx.exception;

import lombok.Data;

@Data
public class WxPayException extends RuntimeException {

    String errorMsg;

    public WxPayException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
}
