package com.berg.base;

import com.berg.message.MessageConstant;
import com.berg.message.Result;

public class BaseController {

    /**
     * 返回请求成功
     * @param msg
     * @param data
     * @return
     */
    public Result getSuccessResult(String msg, Object data){
        return new Result(MessageConstant.SYSTEM_SUCESS_CODE,msg,data);
    }

    /**
     * 返回请求失败
     * @param msg
     * @param data
     * @return
     */
    public Result getFailResult(String msg,Object data){
        return new Result(MessageConstant.SYSTEM_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求参数异常
     * @param msg
     * @param data
     * @return
     */
    public Result getParameterResult(String msg,Object data){
        return new Result(MessageConstant.PARAMETER_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求未授权
     * @param msg
     * @param data
     * @return
     */
    public Result getUnoauthResult(String msg,Object data){
        return new Result(MessageConstant.UNAUTH_ERROR_CODE,msg,data);
    }

    /**
     * 返回请求用户友好提示
     * @param msg
     * @param data
     * @return
     */
    public Result getFriendlyResult(String msg,Object data){
        return new Result(MessageConstant.USER_FRIENDLY_ERROR_CODE,msg,data);
    }
}
