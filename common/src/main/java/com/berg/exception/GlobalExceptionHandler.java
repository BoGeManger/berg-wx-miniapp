package com.berg.exception;

import com.berg.message.MessageConstant;
import com.berg.message.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex) {
		Result result = getResult(ex,true);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	public Result getResult(Throwable ex,Boolean islog){
		Result result;
		if (ex instanceof AppException) {
			AppException e = (AppException) ex;
			result = new Result(e.getErrorCode(), e.getErrorMsg(), null,HttpStatus.OK);
			if(islog){
				log.error(e.getErrorMsg());
			}
		} else if (ex instanceof MethodArgumentNotValidException) {
			if(islog) {
				log.error("请求参数错误", ex);
			}
			List<String> errList = new ArrayList<>();
			MethodArgumentNotValidException e = (MethodArgumentNotValidException) ex;
			List<ObjectError> list = e.getBindingResult().getAllErrors();
			list.forEach(item -> {
				errList.add(item.getDefaultMessage());
			});
			result = new Result(MessageConstant.PARAMETER_ERROR_CODE, StringUtils.join(errList, ";"), null,HttpStatus.BAD_REQUEST);
		} else if (ex instanceof BindException) {
			if(islog) {
				log.error("请求参数错误", ex);
			}
			List<String> errList = new ArrayList<>();
			BindException e = (BindException) ex;
			List<ObjectError> list = e.getBindingResult().getAllErrors();
			list.forEach(item -> {
				errList.add(item.getDefaultMessage());
			});
			result = new Result(MessageConstant.PARAMETER_ERROR_CODE, StringUtils.join(errList, ";"), null,HttpStatus.BAD_REQUEST);
		}else if (ex instanceof ConstraintViolationException) {
			if(islog) {
				log.error("请求参数错误", ex);
			}
			ConstraintViolationException e = (ConstraintViolationException) ex;
			String errMsg = e.getConstraintViolations().stream().map((cv) -> cv.getMessage()).collect(Collectors.joining(";"));
			result = new Result(MessageConstant.PARAMETER_ERROR_CODE, errMsg, null,HttpStatus.BAD_REQUEST);
		} else if (ex instanceof AuthenticationException) {
			if(islog) {
				log.error("授权错误", ex);
			}
			AuthenticationException e = (AuthenticationException) ex;
			String errMsg = e.getMessage();
			result = new Result(MessageConstant.UNAUTH_ERROR_CODE, errMsg, null,HttpStatus.UNAUTHORIZED);
		} else if (ex instanceof UnauthorizedException) {
			if(islog) {
				log.error("授权错误", ex);
			}
			UnauthorizedException e = (UnauthorizedException) ex;
			result = new Result(MessageConstant.USER_FRIENDLY_ERROR_CODE, "您没有对应的权限操作，请联系管理员授权", null,HttpStatus.BAD_REQUEST);
		} else {
			if(islog) {
				log.error("运行异常", ex);
			}
			result = new Result(MessageConstant.SYSTEM_ERROR_CODE, "运行异常", ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
}
