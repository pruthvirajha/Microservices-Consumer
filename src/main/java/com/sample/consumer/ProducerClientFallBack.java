package com.sample.consumer;

import org.springframework.stereotype.Component;

@Component
public class ProducerClientFallBack implements ProducerClient{

	@Override
	public String testApi() {
		return "falback-response";
	}
	
}
