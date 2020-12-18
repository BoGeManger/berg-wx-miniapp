package com.berg.common.swagger;

public enum  ErrorEnum {

    OK(200,"请求成功"),
    INTERNAL_SERVER_ERROR(500,"系统内部错误"),
    BAD_REQUEST(400,"请求参数错误"),
    UNAUTHORIZED(401,"授权失败"),
    EXPECTATION_FAILED(417,"用户友好提示");

    Integer key;
    String value;

    ErrorEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey(){
        return this.key;
    }

    public String getValue(){
        return this.value;
    }
}
