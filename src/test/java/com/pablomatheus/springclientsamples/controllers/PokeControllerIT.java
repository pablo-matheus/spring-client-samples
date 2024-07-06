package com.pablomatheus.springclientsamples.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PokeControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testFetchDittoDataWithRestTemplate() throws Exception {
        mockMvc.perform(get("/api/v1/poke/rest-template").param("name", "ditto"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ditto")));
    }

    @Test
    void testFetchDittoDataWithWebClient() {
        webTestClient.get()
                .uri(String.format("http://localhost:%d/api/v1/poke/web-client?name={name}", port), "ditto")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains("ditto"));
    }

    @Test
    void testFetchDittoDataWithWebClientBlocking() {
        webTestClient.get()
                .uri(String.format("http://localhost:%d/api/v1/poke/web-client-blocking?name={name}", port), "ditto")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains("ditto"));
    }

    @Test
    void testFetchDittoDataWithSpringInterfaceClient() throws Exception {
        mockMvc.perform(get("/api/v1/poke/spring-interface-client").param("name", "ditto"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ditto")));
    }

    @Test
    void testFetchDittoDataWithFeignClient() throws Exception {
        mockMvc.perform(get("/api/v1/poke/feign-client").param("name", "ditto"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ditto")));
    }

    @Test
    void testFetchDataWithRestTemplateError() throws Exception {
        mockMvc.perform(get("/api/v1/poke/rest-template").param("name", "unknown"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));
    }

    @Test
    void testFetchDataWithWebClientError() {
        webTestClient.get()
                .uri(String.format("http://localhost:%d/api/v1/poke/web-client?name={name}", port), "unknown")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains("Not Found"));
        ;
    }

    @Test
    void testFetchDataWithWebClientBlockingError() {
        webTestClient.get()
                .uri(String.format("http://localhost:%d/api/v1/poke/web-client-blocking?name={name}", port), "unknown")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .consumeWith(response -> assertThat(response.getResponseBody()).contains("Not Found"));
        ;
    }

    @Test
    void testFetchDataWithSpringInterfaceClientError() throws Exception {
        mockMvc.perform(get("/api/v1/poke/spring-interface-client").param("name", "unknown"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));
    }

    @Test
    void testFetchDataWithFeignClientError() throws Exception {
        mockMvc.perform(get("/api/v1/poke/feign-client").param("name", "unknown"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));

    }

}
