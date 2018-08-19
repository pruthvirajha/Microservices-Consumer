package com.sample.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "PRODUCER", fallback=ProducerClientFallBack.class)
public interface ProducerClient {

	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public String testApi();
	
}



