package com.pablomatheus.springclientsamples.configurations;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HttpClientErrorException.class})
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException exception) {
        log.error("Error while using RestTemplate or Spring Interface Client", exception);
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsString());
    }

    @ExceptionHandler({FeignException.class})
    public ResponseEntity<String> handleFeignException(FeignException exception) {
        log.error("Error while using OpenFeign", exception);
        return ResponseEntity.status(exception.status()).body(exception.contentUTF8());
    }

    @ExceptionHandler({WebClientResponseException.class})
    public ResponseEntity<String> handleWebClientResponseException(WebClientResponseException exception) {
        log.error("Error while using WebClient", exception);
        return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception) {
        log.error("An unexpected error occurred", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + exception.getMessage());
    }

}
