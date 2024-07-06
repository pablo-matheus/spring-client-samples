package com.pablomatheus.springclientsamples.services;

import com.pablomatheus.springclientsamples.clients.PokeFeignClient;
import com.pablomatheus.springclientsamples.clients.PokeHttpClient;
import com.pablomatheus.springclientsamples.clients.PokeRestTemplateClient;
import com.pablomatheus.springclientsamples.clients.PokeWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class PokeService {

    private final PokeRestTemplateClient pokeRestTemplateClient;
    private final PokeWebClient pokeWebClient;
    private final PokeHttpClient pokeHttpClient;
    private final PokeFeignClient pokeFeignClient;

    public String fetchPokemonDataWithRestTemplate(String name) {
        return pokeRestTemplateClient.getPokemon(name);
    }

    public Mono<String> fetchPokemonDataWithWebClient(String name) {
        return pokeWebClient.getPokemon(name);
    }

    public String fetchPokemonDataWithWebClientBlockingCall(String name) {
        return pokeWebClient.getPokemon(name).block();
    }

    public String fetchPokemonDataWithSpringInterfaceClient(String name) {
        return pokeHttpClient.getPokemon(name);
    }

    public String fetchPokemonDataWithFeignClient(String name) {
        return pokeFeignClient.getPokemon(name);
    }

}
