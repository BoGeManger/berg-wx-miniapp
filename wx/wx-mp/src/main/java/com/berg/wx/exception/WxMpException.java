package com.berg.wx.exception;

import lombok.Data;

@Data
public class WxMpException extends RuntimeException {

    String errorMsg;

    public WxMpException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
}
