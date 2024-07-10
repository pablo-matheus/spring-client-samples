package com.pablomatheus.springclientsamples.configurations;

import com.pablomatheus.springclientsamples.clients.PokeHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PokeHttpClientConfig {

    @Value("${poke-api.base-url}")
    private String baseUrl;

    @Bean
    public PokeHttpClient pokeHttpClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(PokeHttpClient.class);
    }

}

