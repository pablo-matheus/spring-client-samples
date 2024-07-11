package com.pablomatheus.springclientsamples.configurations;

import com.pablomatheus.springclientsamples.clients.PokeHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class PokeHttpClientConfig {

    @Value("${poke-api.base-url}")
    private String baseUrl;

    @Value("${rest-client.connection-timeout}")
    private long connectionTimeout;

    @Value("${rest-client.response-timeout}")
    private long responseTimeout;

    @Bean
    public PokeHttpClient pokeHttpClient() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofMillis(connectionTimeout))
                .withReadTimeout(Duration.ofMillis(responseTimeout));

        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(settings);

        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(PokeHttpClient.class);
    }

}

