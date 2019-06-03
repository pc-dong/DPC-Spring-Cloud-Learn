package com.dpc.framework.common.exception;

import com.dpc.framework.common.beans.Error;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;

/**
 * @author dongpeichao
 * @version v1.0
 */
@Getter
@Setter
public class FeignBadResponseWrapper extends HystrixBadRequestException {
	private final int status;
	private final HttpHeaders headers;
	private final Error error;

	public FeignBadResponseWrapper(int status, HttpHeaders headers, Error error) {
		super("Bad request");
		this.status = status;
		this.headers = headers;
		this.error = error;
	}
}

