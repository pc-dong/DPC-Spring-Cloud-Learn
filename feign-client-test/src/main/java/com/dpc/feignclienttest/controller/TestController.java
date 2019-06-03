package com.dpc.feignclienttest.controller;

import com.alibaba.fastjson.JSONObject;
import com.dpc.feignclienttest.model.User;
import com.dpc.feignclienttest.feign.UaaServiceClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author dongpeichao
 * @version v1.0
 * @email dongpeichao@boco.com.cn
 * @time 2019年05月28日16:08
 * @modify <BR/>
 * 修改内容：<BR/>
 * 修改人员：<BR/>
 * 修改时间：<BR/>
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试")
@Slf4j
public class TestController {
	@Autowired
	private UaaServiceClient uaaServiceClient;
	@Autowired
	@Qualifier("loadBalance")
	private RestTemplate loadBalance;


	@GetMapping("/{userId}")
	public User get(@PathVariable("userId") long userId) {
		return uaaServiceClient.get(userId);
	}

	@GetMapping("/ribbon")
	public User getRibbon(long userId) {
		ResponseEntity<String> responseEntity = loadBalance.getForEntity("http://authentication-server/users/" + userId, String.class);
		return JSONObject.parseObject(responseEntity.getBody(), User.class);
	}
}
