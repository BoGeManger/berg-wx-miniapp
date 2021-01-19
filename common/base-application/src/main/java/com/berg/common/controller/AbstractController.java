package com.berg.common.controller;

import com.berg.common.constant.MessageConstants;
import com.berg.common.constant.Result;
import org.springframework.http.HttpStatus;

public class AbstractController {

    /**
     * 返回请求成功
     * @param msg
     * @param data
     * @return
     */
    public Result getSuccessResult(String msg, Object data){
        return new Result(MessageConstants.SYSTEM_SUCESS_CODE,msg,data);
    }

    /**
     * 返回请求成功
     * @param msg
     * @param data
     * @param httpStatus
     * @return
     */
    public Result getSuccessResult(String msg, Object data,HttpStatus httpStatus){
        return new Result(MessageConstants.SYSTEM_SUCESS_CODE,msg,data,httpStatus);
    }

    /**
     * 返回请求失败
     * @param msg
     * @param data
     * @return
     */
    public Result getFailResult(String msg,Object data){
        return new Result(MessageConstants.SYSTEM_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求失败
     * @param msg
     * @param data
     * @param httpStatus
     * @return
     */
    public Result getFailResult(String msg,Object data,HttpStatus httpStatus){
        return new Result(MessageConstants.SYSTEM_ERROR_CODE,msg,data,httpStatus);
    }

    /**
     * 返回请求参数异常
     * @param msg
     * @param data
     * @return
     */
    public Result getParameterResult(String msg,Object data){
        return new Result(MessageConstants.PARAMETER_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求参数异常
     * @param msg
     * @param data
     * @param httpStatus
     * @return
     */
    public Result getParameterResult(String msg,Object data,HttpStatus httpStatus){
        return new Result(MessageConstants.PARAMETER_ERROR_CODE,msg,data,httpStatus);
    }

    /**
     * 返回请求未授权
     * @param msg
     * @param data
     * @return
     */
    public Result getUnoauthResult(String msg,Object data){
        return new Result(MessageConstants.UNAUTH_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求未授权
     * @param msg
     * @param data
     * @param httpStatus
     * @return
     */
    public Result getUnoauthResult(String msg,Object data,HttpStatus httpStatus){
        return new Result(MessageConstants.UNAUTH_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求用户友好提示
     * @param msg
     * @param data
     * @return
     */
    public Result getFriendlyResult(String msg,Object data){
        return new Result(MessageConstants.USER_FRIENDLY_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求用户友好提示
     * @param msg
     * @param data
     * @param httpStatus
     * @return
     */
    public Result getFriendlyResult(String msg,Object data,HttpStatus httpStatus){
        return new Result(MessageConstants.USER_FRIENDLY_ERROR_CODE,msg,data);
    }
}
