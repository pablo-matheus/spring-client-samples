package com.pablomatheus.springclientsamples.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class PokeRestTemplateClient {

    @Value("${poke-api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public String getPokemon(String name) {
        return restTemplate.getForObject(baseUrl + "/api/v2/pokemon/{name}", String.class, name);
    }

}
