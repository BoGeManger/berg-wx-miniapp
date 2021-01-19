package com.berg.common.exception;

import com.berg.common.constant.MessageConstants;
import com.berg.common.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * 运行异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception ex) {
		log.error("运行异常", ex);
		Result result = new Result(MessageConstants.SYSTEM_ERROR_CODE, "运行异常", ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 请求参数错误
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		log.error("请求参数错误", ex.getMessage());
		List<String> errList = new ArrayList<>();
		List<ObjectError> list = ex.getBindingResult().getAllErrors();
		list.forEach(item -> {
			errList.add(item.getDefaultMessage());
		});
		Result result = new Result(MessageConstants.PARAMETER_ERROR_CODE,"请求参数错误", StringUtils.join(errList, ";"),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 请求参数错误
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Object> handleBindException(BindException ex) {
		log.error("请求参数错误", ex.getMessage());
		List<String> errList = new ArrayList<>();
		List<ObjectError> list = ex.getBindingResult().getAllErrors();
		list.forEach(item -> {
			errList.add(item.getDefaultMessage());
		});
		Result result = new Result(MessageConstants.PARAMETER_ERROR_CODE,"请求参数错误", StringUtils.join(errList, ";"),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 请求参数错误
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {
		log.error("请求参数错误", ex.getMessage());
		String errMsg = ex.getConstraintViolations().stream().map((cv) -> cv.getMessage()).collect(Collectors.joining(";"));
		Result result = new Result(MessageConstants.PARAMETER_ERROR_CODE, "请求参数错误",errMsg,HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 请求授权错误
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
		log.error("请求授权错误", ex.getMessage());
		Result result = new Result(MessageConstants.UNAUTH_ERROR_CODE, "请求授权错误", ex.getMessage(),HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	//region 自定义错误处理
	/**
	 * 自定义返回异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(AppException.class)
	public ResponseEntity<Object> handleAppException(AppException ex) {
		log.error(ex.getErrorMsg());
		Result result = new Result(ex.getErrorCode(), "自定义返回异常",ex.getErrorMsg(),HttpStatus.OK);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 运行异常
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(FailException.class)
	public ResponseEntity<Object> handleFailException(FailException ex) {
		log.error("运行异常", ex.getMessage());
		Result result = new Result(ex.getErrorCode(), "运行异常", ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 请求参数错误
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ParamException.class)
	public ResponseEntity<Object> handleParamException(ParamException ex) {
		log.error("请求参数错误", ex.getMessage());
		Result result = new Result(ex.getErrorCode(), "请求参数错误", ex.getMessage(),HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 请求授权错误
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UnauthException.class)
	public ResponseEntity<Object> handleUnauthException(UnauthException ex) {
		log.error("请求授权错误", ex.getMessage());
		Result result = new Result(ex.getErrorCode(), "请求授权错误", ex.getMessage(),HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}

	/**
	 * 用户友好提示
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(UserFriendException.class)
	public ResponseEntity<Object> handleUserFriendException(UserFriendException ex) {
		log.error("用户友好提示", ex.getMessage());
		Result result = new Result(ex.getErrorCode(), "用户友好提示", ex.getMessage(),HttpStatus.EXPECTATION_FAILED);
		return new ResponseEntity<Object>(result, result.getHttpStatus());
	}
	//endregion

	public  ResponseEntity<Object> getResponseEntity(Throwable throwable){
		ResponseEntity<Object> responseEntity = null;
		if(throwable instanceof MethodArgumentNotValidException){
			responseEntity = handleMethodArgumentNotValidException((MethodArgumentNotValidException)throwable);
		}else if(throwable instanceof BindException){
			responseEntity = handleBindException((BindException)throwable);
		}else if(throwable instanceof ConstraintViolationException){
			responseEntity = handleConstraintViolationException((ConstraintViolationException)throwable);
		}else if(throwable instanceof AuthenticationException){
			responseEntity = handleAuthenticationException((AuthenticationException)throwable);
		} else if(throwable instanceof AppException){
			responseEntity = handleAppException((AppException)throwable);
		}else if(throwable instanceof FailException){
			responseEntity = handleFailException((FailException)throwable);
		}else if(throwable instanceof ParamException){
			responseEntity = handleParamException((ParamException)throwable);
		}else if(throwable instanceof UnauthException){
			responseEntity = handleUnauthException((UnauthException)throwable);
		}else if(throwable instanceof UserFriendException){
			responseEntity = handleUserFriendException((UserFriendException)throwable);
		}else{
			responseEntity = handleException((Exception)throwable);
		}
		return responseEntity;
	}
}
