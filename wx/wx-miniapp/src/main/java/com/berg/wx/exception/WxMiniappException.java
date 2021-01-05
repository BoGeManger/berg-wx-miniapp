package com.berg.wx.exception;

import lombok.Data;

@Data
public class WxMiniappException extends RuntimeException {

    String errorMsg;

    public WxMiniappException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }
}
