package com.eazybytes.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import java.time.Duration;
import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

    @Bean
    public RouteLocator routerLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route( pth -> pth
                        .path("/eazybank/accounts/**")
                        .filters(flt -> flt
                                .rewritePath("eazybank/accounts/(?<segment>.*)", "/${segment}") //riscrive il path ricevuto in modo da mapparlo con quello originale del microservizio
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())//aggiunge un header alla risposta
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")//creo un cicuit-breaker per accounts-MS e gli do il nome accountsCircuitBreaker
                                        .setFallbackUri("forward:/contactSupport")) //indico l'endpoint da invocare per la risposta di fallback
                        )
                        .uri("lb://accounts")
                )
                .route( pth -> pth
                        .path("/eazybank/loans/**")
                        .filters(flt -> flt.rewritePath("eazybank/loans/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .retry(retryConfig -> retryConfig
                                        .setRetries(3)
                                        .setMethods(HttpMethod.GET)
                                        .setBackoff(Duration.ofMillis(100), Duration.ofMillis(1000), 2, true)))
                        .uri("lb://loans")
                )
                .route( pth -> pth
                        .path("/eazybank/cards/**")
                        .filters(flt -> flt.rewritePath("eazybank/cards/(?<segment>.*)", "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
                        .uri("lb://cards")
                )
                .build();
    }

}
