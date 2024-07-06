package com.pablomatheus.springclientsamples.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

@HttpExchange(url = "https://pokeapi.co/api/v2")
public interface PokeHttpClient {

    @GetExchange("/pokemon/{name}")
    String getPokemon(@PathVariable String name);

}
