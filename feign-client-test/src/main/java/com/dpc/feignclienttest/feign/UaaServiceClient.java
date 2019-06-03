package com.dpc.feignclienttest.feign;

import com.dpc.feignclienttest.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author dongpeichao
 */
@FeignClient(value = "authentication-server", fallback = UaaServiceClientFallback.class)
public interface UaaServiceClient {
	@GetMapping(value = "/users/{userId}")
	User get(@PathVariable("userId") long userId);
}
