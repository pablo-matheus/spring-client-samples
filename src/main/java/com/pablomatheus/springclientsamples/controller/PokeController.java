package com.pablomatheus.springclientsamples.controller;

import com.pablomatheus.springclientsamples.services.PokeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Pokémon", description = "API for fetching Pokémon data")
public class PokeController {

    private final PokeService pokeService;

    public PokeController(PokeService pokeService) {
        this.pokeService = pokeService;
    }

    @Operation(summary = "Fetch Pokémon data using RestTemplate")
    @GetMapping("/fetch/rest-template")
    public String fetchPokemonDataWithRestTemplate(@RequestParam String name) {
        return pokeService.fetchPokemonDataWithRestTemplate(name);
    }

    @Operation(summary = "Fetch Pokémon data using WebClient")
    @GetMapping("/fetch/web-client")
    public Mono<String> fetchPokemonDataWithWebClient(@RequestParam String name) {
        return pokeService.fetchPokemonDataWithWebClient(name);
    }

    @Operation(summary = "Fetch Pokémon data using Spring Interface Client")
    @GetMapping("/fetch/spring-interface-client")
    public String fetchPokemonDataWithSpringInterfaceClient(@RequestParam String name) {
        return pokeService.fetchPokemonDataWithSpringInterfaceClient(name);
    }

    @Operation(summary = "Fetch Pokémon data using Feign Client")
    @GetMapping("/fetch/feign-client")
    public String fetchPokemonDataWithFeignClient(@RequestParam String name) {
        return pokeService.fetchPokemonDataWithFeignClient(name);
    }

}
