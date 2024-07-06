package com.pablomatheus.springclientsamples.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pokeFeignClient", url = "https://pokeapi.co/api/v2")
public interface PokeFeignClient {

    @GetMapping("/pokemon/{name}")
    String getPokemon(@PathVariable String name);

}
