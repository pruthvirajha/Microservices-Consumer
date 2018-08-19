package com.sample.consumer;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = { "feign.hystrix.enabled=true" })
@ContextConfiguration(classes = { ProducerClientTest.LocalRibbonClientConfiguration.class })
public class ProducerClientTest {
	
	@Autowired
	public ProducerClient producerClient;

	@ClassRule
    public static WireMockClassRule wiremock = new WireMockClassRule(8089);

	@Before
	public void setup() throws IOException {
	        stubFor(get(urlEqualTo("/test"))
	                .willReturn(aResponse()
	                        .withStatus(HttpStatus.OK.value())
	                        .withHeader("Content-Type", "text/json")
	                        .withBody("cool")));
	}
	
	
	@Test
    public void testFindById() {
		String result = producerClient.testApi();

        assertNotNull("should not be null", result);
        assertEquals("cool",result);
    }
	
	@TestConfiguration
	public static class LocalRibbonClientConfiguration {
		@Bean
		public ServerList<Server> ribbonServerList() {
			return new StaticServerList<>(new Server("localhost", wiremock.port()));
		}
	}
}
