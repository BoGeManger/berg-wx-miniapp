package com.berg.common.constant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@ApiModel(value = "接口返回类")
public class Result<T> implements Serializable {

    /**
     * 返回编码(00：请求成功,99：系统异常，10：参数异常，11：未授权，66：用户友好提示)
     */
    @ApiModelProperty(value = "返回编码")
    String code;

    /**
     * 消息
     */
    @ApiModelProperty(value = "消息")
    String message;

    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    T data;

    @JsonIgnore
    HttpStatus httpStatus;

    public Result(String errorCode, String errorMsg, T data) {
        this.code = errorCode;
        this.message = errorMsg;
        this.data = data;
        this.httpStatus = HttpStatus.OK;
    }

    public Result(String errorCode, String errorMsg, T data,HttpStatus httpStatus) {
        this.code = errorCode;
        this.message = errorMsg;
        this.data = data;
        this.httpStatus = httpStatus;
    }

}
