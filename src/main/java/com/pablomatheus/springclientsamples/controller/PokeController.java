package com.pablomatheus.springclientsamples.controller;

import com.pablomatheus.springclientsamples.services.PokeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/poke")
@Tag(name = "Pokémon", description = "API for fetching Pokémon data")
public class PokeController {

    private final PokeService pokeService;

    @Operation(summary = "Fetch Pokémon data using RestTemplate")
    @GetMapping("/rest-template")
    public ResponseEntity<String> fetchPokemonDataWithRestTemplate(@RequestParam String name) {
        return ResponseEntity.ok(pokeService.fetchPokemonDataWithRestTemplate(name));
    }

    @Operation(summary = "Fetch Pokémon data using WebClient")
    @GetMapping("/web-client")
    public ResponseEntity<Mono<String>> fetchPokemonDataWithWebClient(@RequestParam String name) {
        return ResponseEntity.ok(pokeService.fetchPokemonDataWithWebClient(name));
    }

    @Operation(summary = "Fetch Pokémon data using Spring Interface Client")
    @GetMapping("/spring-interface-client")
    public ResponseEntity<String> fetchPokemonDataWithSpringInterfaceClient(@RequestParam String name) {
        return ResponseEntity.ok(pokeService.fetchPokemonDataWithSpringInterfaceClient(name));
    }

    @Operation(summary = "Fetch Pokémon data using Feign Client")
    @GetMapping("/feign-client")
    public ResponseEntity<String> fetchPokemonDataWithFeignClient(@RequestParam String name) {
        return ResponseEntity.ok(pokeService.fetchPokemonDataWithFeignClient(name));
    }

}
