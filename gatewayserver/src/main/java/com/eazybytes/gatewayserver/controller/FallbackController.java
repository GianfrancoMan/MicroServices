package com.eazybytes.gatewayserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/*Questo controller è usato per restituire una risposta come fallback nel caso il circuit breaker vada in stato di OPEN*/
@RestController
public class FallbackController {

    @RequestMapping("contactSupport")
    public Mono<String> contactSupport() {
        return Mono.just("An error occurre. please try after some time or contact support team");
    }
}
