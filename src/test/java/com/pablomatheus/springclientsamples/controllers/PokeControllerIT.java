package com.pablomatheus.springclientsamples.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testFetchPokemonDataWithRestTemplate() throws Exception {
        String name = "ditto";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/rest-template").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    void testFetchPokemonDataWithWebClient() {
        String name = "ditto";

        webTestClient.get()
                .uri("/api/v1/poke/web-client?name={name}", name)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains(name));
    }

    @Test
    void testFetchPokemonDataWithWebClientBlocking() {
        String name = "ditto";

        webTestClient.get()
                .uri("/api/v1/poke/web-client-blocking?name={name}", name)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains(name));
    }

    @Test
    void testFetchPokemonDataWithSpringInterfaceClient() throws Exception {
        String name = "ditto";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/spring-interface-client").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    void testFetchPokemonDataWithFeignClient() throws Exception {
        String name = "ditto";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/feign-client").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    void testFetchPokemonDataWithRestTemplateError() throws Exception {
        String name = "unknown";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/rest-template").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));
    }

    @Test
    void testFetchPokemonDataWithWebClientError() {
        String name = "unknown";

        webTestClient.get()
                .uri("/api/v1/poke/web-client?name={name}", name)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains("Not Found"));
    }

    @Test
    void testFetchPokemonDataWithWebClientBlockingError() {
        String name = "unknown";

        webTestClient.get()
                .uri("/api/v1/poke/web-client-blocking?name={name}", name)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains("Not Found"));
    }

    @Test
    void testFetchPokemonDataWithSpringInterfaceClientError() throws Exception {
        String name = "unknown";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/spring-interface-client").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));
    }

    @Test
    void testFetchPokemonDataWithFeignClientError() throws Exception {
        String name = "unknown";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/feign-client").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));

    }

}
