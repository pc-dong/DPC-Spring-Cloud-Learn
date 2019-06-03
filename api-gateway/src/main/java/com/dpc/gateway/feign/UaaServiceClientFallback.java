package com.dpc.gateway.feign;

import com.dpc.framework.common.exception.ErrorInfoException;
import com.dpc.gateway.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author dongpeichao
 */
@Component
public class UaaServiceClientFallback implements UaaServiceClient {
	@Override
	public User get( long userId) {
		throw new ErrorInfoException("获取用户失败");
	}
}
