package com.pablomatheus.springclientsamples.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Value("${rest-template.response-timeout}")
    private int responseTimeout;

    @Value("${rest-template.connection-timeout}")
    private int connectionTimeout;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(getClientHttpRequestFactory());
    }

    private SimpleClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setReadTimeout(responseTimeout);
        clientHttpRequestFactory.setConnectTimeout(connectionTimeout);
        return clientHttpRequestFactory;
    }

}
