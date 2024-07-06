package com.pablomatheus.springclientsamples.configurations;

import com.pablomatheus.springclientsamples.clients.PokeHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PokeHttpClientConfig {

    @Bean
    public PokeHttpClient pokeHttpClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl("https://pokeapi.co/api/v2")
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(PokeHttpClient.class);
    }

}

