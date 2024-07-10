package com.pablomatheus.springclientsamples.clients;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface PokeHttpClient {

    @GetExchange("/api/v2/pokemon/{name}")
    String getPokemon(@PathVariable String name);

}
