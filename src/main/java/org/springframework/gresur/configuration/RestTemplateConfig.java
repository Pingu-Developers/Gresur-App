package org.springframework.gresur.configuration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestTemplateConfig {

	@Bean
	public WebClient localApiClient() {
	    return WebClient.create("https://www.distancia.co");
	}
	
	
}