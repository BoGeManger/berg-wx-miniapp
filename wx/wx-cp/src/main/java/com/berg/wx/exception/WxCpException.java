package com.berg.wx.exception;

import lombok.Data;

@Data
public class WxCpException extends RuntimeException {

    String errorMsg;

    public WxCpException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
}
