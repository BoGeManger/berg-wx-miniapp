package com.berg.common.controller;

import com.berg.common.constant.MessageConstants;
import com.berg.common.constant.Result;
import com.berg.common.function.VoidConsumer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Supplier;

public abstract class AbstractController {

    //region 返回自定义编码

    /**
     * 返回自定义编码
     * @param code
     * @param msg
     * @return
     */
    public Result customize(String code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 返回自定义编码
     * @param code
     * @param msg
     * @param voidConsumer
     * @return
     */
    public Result<Void> customize(String code, String msg, VoidConsumer voidConsumer) {
        voidConsumer.accept();
        return new Result(code, msg, null);
    }

    /**
     * 返回自定义编码
     * @param code
     * @param msg
     * @param voidConsumer
     * @param httpStatus
     * @return
     */
    public Result<Void> customize(String code, String msg, VoidConsumer voidConsumer, HttpStatus httpStatus) {
        voidConsumer.accept();
        return new Result(code, msg, null,httpStatus);
    }

    /**
     * 返回自定义编码
     * @param code
     * @param msg
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> Result<T> customize(String code, String msg, Supplier<T> supplier) {
        return new Result(code, msg, supplier.get());
    }

    /**
     * 返回自定义编码
     * @param code
     * @param msg
     * @param supplier
     * @param httpStatus
     * @param <T>
     * @return
     */
    public <T> Result<T> customize(String code, String msg, Supplier<T> supplier, HttpStatus httpStatus) {
        return new Result(code, msg, supplier.get(),httpStatus);
    }
    //endregion

    //region 返回请求成功

    /**
     * 返回请求成功
     * @param msg
     * @return
     */
    public Result success(String msg) {
        return customize(MessageConstants.SYSTEM_SUCESS_CODE, msg);
    }

    /**
     * 返回请求成功
     * @param msg
     * @param voidConsumer
     * @return
     */
    public Result<Void> success(String msg, VoidConsumer voidConsumer) {
        return customize(MessageConstants.SYSTEM_SUCESS_CODE, msg, voidConsumer);
    }

    /**
     * 返回请求成功
     * @param msg
     * @param voidConsumer
     * @param httpStatus
     * @return
     */
    public Result<Void> success(String msg, VoidConsumer voidConsumer, HttpStatus httpStatus) {
        return customize(MessageConstants.SYSTEM_SUCESS_CODE, msg, voidConsumer, httpStatus);
    }

    /**
     * 返回请求成功
     * @param msg
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> Result<T> success(String msg, Supplier<T> supplier) {
        return customize(MessageConstants.SYSTEM_SUCESS_CODE, msg, supplier);
    }

    /**
     * 返回请求成功
     * @param msg
     * @param supplier
     * @param httpStatus
     * @param <T>
     * @return
     */
    public <T> Result<T> success(String msg, Supplier<T> supplier, HttpStatus httpStatus) {
        return customize(MessageConstants.SYSTEM_SUCESS_CODE, msg, supplier, httpStatus);
    }
    //endregion

    //region 返回请求失败

    /**
     * 返回请求失败
     * @param msg
     * @param voidConsumer
     * @return
     */
    public Result<Void> fail(String msg, VoidConsumer voidConsumer) {
        return customize(MessageConstants.SYSTEM_ERROR_CODE, msg, voidConsumer);
    }

    /**
     * 返回请求失败
     * @param msg
     * @param voidConsumer
     * @param httpStatus
     * @return
     */
    public Result<Void> fail(String msg, VoidConsumer voidConsumer, HttpStatus httpStatus) {
        return customize(MessageConstants.SYSTEM_ERROR_CODE, msg, voidConsumer, httpStatus);
    }

    /**
     * 返回请求失败
     * @param msg
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> Result<T> fail(String msg, Supplier<T> supplier) {
        return customize(MessageConstants.SYSTEM_ERROR_CODE, msg, supplier);
    }

    /**
     * 返回请求失败
     * @param msg
     * @param supplier
     * @param httpStatus
     * @param <T>
     * @return
     */
    public <T> Result<T> fail(String msg, Supplier<T> supplier, HttpStatus httpStatus) {
        return customize(MessageConstants.SYSTEM_ERROR_CODE, msg, supplier, httpStatus);
    }
    //endregion

    //region 返回请求参数异常

    /**
     * 返回请求参数异常
     * @param msg
     * @param voidConsumer
     * @return
     */
    public Result<Void> parameter(String msg, VoidConsumer voidConsumer) {
        return customize(MessageConstants.PARAMETER_ERROR_CODE, msg, voidConsumer);
    }

    /**
     * 返回请求参数异常
     * @param msg
     * @param voidConsumer
     * @param httpStatus
     * @return
     */
    public Result<Void> parameter(String msg, VoidConsumer voidConsumer, HttpStatus httpStatus) {
        return customize(MessageConstants.PARAMETER_ERROR_CODE, msg, voidConsumer, httpStatus);
    }

    /**
     * 返回请求参数异常
     * @param msg
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> Result<T> parameter(String msg, Supplier<T> supplier) {
        return customize(MessageConstants.PARAMETER_ERROR_CODE, msg, supplier);
    }

    /**
     * 返回请求参数异常
     * @param msg
     * @param supplier
     * @param httpStatus
     * @param <T>
     * @return
     */
    public <T> Result<T> parameter(String msg, Supplier<T> supplier, HttpStatus httpStatus) {
        return customize(MessageConstants.PARAMETER_ERROR_CODE, msg, supplier, httpStatus);
    }
    //endregion

    //region 返回请求未授权

    /**
     * 返回请求未授权
     * @param msg
     * @param voidConsumer
     * @return
     */
    public Result<Void> unoauth(String msg, VoidConsumer voidConsumer) {
        return customize(MessageConstants.UNAUTH_ERROR_CODE, msg, voidConsumer);
    }

    /**
     * 返回请求未授权
     * @param msg
     * @param voidConsumer
     * @param httpStatus
     * @return
     */
    public Result<Void> unoauth(String msg, VoidConsumer voidConsumer, HttpStatus httpStatus) {
        return customize(MessageConstants.UNAUTH_ERROR_CODE, msg, voidConsumer, httpStatus);
    }

    /**
     * 返回请求未授权
     * @param msg
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> Result<T> unoauth(String msg, Supplier<T> supplier) {
        return customize(MessageConstants.UNAUTH_ERROR_CODE, msg, supplier);
    }

    /**
     * 返回请求未授权
     * @param msg
     * @param supplier
     * @param httpStatus
     * @param <T>
     * @return
     */
    public <T> Result<T> unoauth(String msg, Supplier<T> supplier, HttpStatus httpStatus) {
        return customize(MessageConstants.UNAUTH_ERROR_CODE, msg, supplier, httpStatus);
    }
    //endregion

    //region 返回请求用户友好提示

    /**
     * 返回请求用户友好提示
     * @param msg
     * @param voidConsumer
     * @return
     */
    public Result<Void> friendly(String msg, VoidConsumer voidConsumer) {
        return customize(MessageConstants.USER_FRIENDLY_ERROR_CODE, msg, voidConsumer);
    }

    /**
     * 返回请求用户友好提示
     * @param msg
     * @param voidConsumer
     * @param httpStatus
     * @return
     */
    public Result<Void> friendly(String msg, VoidConsumer voidConsumer, HttpStatus httpStatus) {
        return customize(MessageConstants.USER_FRIENDLY_ERROR_CODE, msg, voidConsumer, httpStatus);
    }

    /**
     * 返回请求用户友好提示
     * @param msg
     * @param supplier
     * @param <T>
     * @return
     */
    public <T> Result<T> friendly(String msg, Supplier<T> supplier) {
        return customize(MessageConstants.USER_FRIENDLY_ERROR_CODE, msg, supplier);
    }

    /**
     * 返回请求用户友好提示
     * @param msg
     * @param supplier
     * @param httpStatus
     * @param <T>
     * @return
     */
    public <T> Result<T> friendly(String msg, Supplier<T> supplier, HttpStatus httpStatus) {
        return customize(MessageConstants.USER_FRIENDLY_ERROR_CODE, msg, supplier, httpStatus);
    }
    //endregion
}
