package com.dpc.framework.common.exception;

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

import java.util.Objects;

/**
 * 全局异常捕获处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Error> exceptionHandler(Exception e) throws Exception {
		// 参数校验提示
		if (e instanceof BindException) {
			return new ResponseEntity<>(Error.error(Objects.requireNonNull(((BindException) e).getFieldError())
					.getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}

		// 参数校验提示
		if (e instanceof MethodArgumentNotValidException) {
			return new ResponseEntity<>(Error.error(
					Objects.requireNonNull(((MethodArgumentNotValidException) e).getBindingResult().getFieldError())
							.getDefaultMessage()), HttpStatus.BAD_REQUEST);
		}

		if (e instanceof IllegalStateException) {
			new ResponseEntity<>(Error.error("参数传递错误"), HttpStatus.BAD_REQUEST);
		}

		if (e instanceof ErrorInfoException) {
			new ResponseEntity<>(Error.error(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		if (e instanceof ErrorInfoException) {
			new ResponseEntity<>(Error.error(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

		if (e instanceof EntityNotFoudException) {
			new ResponseEntity<>(Error.error(e.getMessage()), HttpStatus.NOT_FOUND);
		}

		log.error("", e);
		throw e;
	}
}
