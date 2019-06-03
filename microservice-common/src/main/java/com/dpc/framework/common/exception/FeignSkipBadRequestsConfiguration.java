package com.dpc.framework.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.dpc.framework.common.beans.Error;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * @author dongpeichao
 * @version v1.0
 */
@Configuration
public class FeignSkipBadRequestsConfiguration {
	@Bean
	public ErrorDecoder errorDecoder() {
		return (methodKey, response) -> {
			int status = response.status();
			if (status == HttpStatus.BAD_REQUEST.value() || status == HttpStatus.NOT_FOUND.value()) {
				String body;
				Error error = null;
				try {
					body = IOUtils.toString(response.body().asReader());
					if(StringUtils.isNotBlank(body)){
						error = JSONObject.parseObject(body, Error.class);
					}
				} catch (Exception ignored) {}
				HttpHeaders httpHeaders = new HttpHeaders();
				response.headers().forEach((k, v) -> httpHeaders.add("feign-" + k, StringUtils.join(v,",")));
				return new FeignBadResponseWrapper(status, httpHeaders, error);
			}
			else {
				return new RuntimeException("Response Code " + status);
			}
		};
	}
}
