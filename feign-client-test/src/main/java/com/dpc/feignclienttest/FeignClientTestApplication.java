package com.dpc.feignclienttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.dpc")
@EnableFeignClients
@EnableDiscoveryClient
public class FeignClientTestApplication {

	@LoadBalanced
	@Bean
	public RestTemplate loadBalance(){
		return new RestTemplate();
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(FeignClientTestApplication.class, args);
	}

}
