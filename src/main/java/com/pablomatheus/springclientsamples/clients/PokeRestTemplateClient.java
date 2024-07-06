package com.pablomatheus.springclientsamples.clients;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class PokeRestTemplateClient {

    private final RestTemplate restTemplate;

    public String getPokemon(String name) {
        return restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/{name}", String.class, name);
    }

}
