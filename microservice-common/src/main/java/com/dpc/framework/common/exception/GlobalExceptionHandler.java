package com.dpc.framework.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.dpc.framework.common.beans.Error;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 全局异常捕获处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(BindException e) {
		return new ResponseEntity<>(Error.error(e.getFieldError()
				.getDefaultMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(MethodArgumentNotValidException e) {
		return new ResponseEntity<>(Error.error(e.getBindingResult().getFieldError()
				.getDefaultMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(IllegalStateException e) {
		log.error("", e);
		return new ResponseEntity<>(Error.error("参数传递错误"), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(ErrorInfoException e) {
		return new ResponseEntity<>(Error.error(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(EntityNotFoudException e) {
		return new ResponseEntity<>(Error.error(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(NoSuchElementException e) {
		return new ResponseEntity<>(Error.error(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(FeignBadResponseWrapper e) {
		return new ResponseEntity<>(e.getError(),
				HttpStatus.valueOf(e.getStatus()));
	}

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(HttpClientErrorException e) {
		Error error = null;
		try {
			String body = e.getResponseBodyAsString();
			error = JSONObject.parseObject(body, Error.class);
		} catch (Exception ignored) {
		}

		return new ResponseEntity<>(null == error ? Error.error(e.getMessage()) : error, e.getStatusCode());
	}
}
