package com.pablomatheus.springclientsamples.controllers;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathTemplate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@WireMockTest(httpPort = 8081)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokeControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testFetchPokemonDataWithRestTemplate() throws Exception {
        String name = "ditto";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withBodyFile("ditto-response.json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/rest-template").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    void testFetchPokemonDataWithWebClient() {
        String name = "ditto";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withBodyFile("ditto-response.json")));

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

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withBodyFile("ditto-response.json")));

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

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withBodyFile("ditto-response.json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/spring-interface-client").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    void testFetchPokemonDataWithFeignClient() throws Exception {
        String name = "ditto";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withBodyFile("ditto-response.json")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/feign-client").param("name", name))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(name)));
    }

    @Test
    void testFetchPokemonDataWithRestTemplateError() throws Exception {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(404).withBody("Not Found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/rest-template").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));
    }

    @Test
    void testFetchPokemonDataWithWebClientError() {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(404).withBody("Not Found")));

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

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(404).withBody("Not Found")));

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

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(404).withBody("Not Found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/spring-interface-client").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));
    }

    @Test
    void testFetchPokemonDataWithFeignClientError() throws Exception {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(404).withBody("Not Found")));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/feign-client").param("name", name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Not Found")));

    }

    @Test
    void testFetchPokemonDataWithRestTemplateResponseTimeout() throws Exception {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withFixedDelay(30000)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/rest-template").param("name", name))
                .andExpect(result -> assertEquals(ResourceAccessException.class, result.getResolvedException().getClass()));
    }

    @Test
    void testFetchPokemonDataWithWebClientResponseTimeout() {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withFixedDelay(30000)));

        webTestClient.get()
                .uri("/api/v1/poke/web-client?name={name}", name)
                .exchange()
                .expectBody()
                .consumeWith(response -> {
                    Exception exception = ((MvcResult) response.getMockServerResult()).getResolvedException();
                    assertEquals(WebClientRequestException.class, exception.getClass());
                });
    }

    @Test
    void testFetchPokemonDataWithWebClientBlockingResponseTimeout() {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withFixedDelay(30000)));

        webTestClient.get()
                .uri("/api/v1/poke/web-client-blocking?name={name}", name)
                .exchange()
                .expectBody()
                .consumeWith(response -> {
                    Exception exception = ((MvcResult) response.getMockServerResult()).getResolvedException();
                    assertEquals(WebClientRequestException.class, exception.getClass());
                });
    }

    @Test
    void testFetchPokemonDataWithSpringInterfaceClientResponseTimeout() throws Exception {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withFixedDelay(30000)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/spring-interface-client").param("name", name))
                .andExpect(result -> assertEquals(ResourceAccessException.class, result.getResolvedException().getClass()));
    }

    @Test
    void testFetchPokemonDataWithFeignClientResponseTimeout() throws Exception {
        String name = "unknown";

        stubFor(get(urlPathTemplate("/api/v2/pokemon/{name}")).withPathParam("name", equalTo(name))
                .willReturn(aResponse().withStatus(200).withFixedDelay(30000)));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/poke/feign-client").param("name", name))
                .andExpect(result -> assertEquals(RetryableException.class, result.getResolvedException().getClass()));
    }

}
