package com.dpc.feignclienttest.feign;

import com.dpc.feignclienttest.model.User;
import com.dpc.framework.common.exception.ErrorInfoException;
import org.springframework.stereotype.Component;

/**
 * @author dongpeichao
 */
@Component
public class UaaServiceClientFallback implements UaaServiceClient {
	@Override
	public User get(long userId) {
		throw new ErrorInfoException("获取用户失败");
	}
}
