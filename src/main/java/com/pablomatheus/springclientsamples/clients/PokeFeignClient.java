package com.pablomatheus.springclientsamples.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pokeFeignClient", url = "${poke-api.base-url}")
public interface PokeFeignClient {

    @GetMapping("/api/v2/pokemon/{name}")
    String getPokemon(@PathVariable String name);

}
