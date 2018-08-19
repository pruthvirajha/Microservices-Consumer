package com.sample.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class ConsumerApplication {
	
	@Autowired
	public ProducerClient producerClient;

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
	
	
	@GetMapping("/test")
	@ResponseBody
	public String testApi() {
		return producerClient.testApi();
	}
	
}
