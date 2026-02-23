package com.eazybytes.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(
                        exchange -> exchange
                                .pathMatchers(HttpMethod.GET).permitAll() //Autorizza chiunque a eseguire richieste GET su qualunque endpoint
                                //proteggo gli endpoint accounts, cards e loans eccetto che per le GET perché il primo pathmatcher  ha priorità sui successivi.
                                .pathMatchers("/eazybank/accounts/**").authenticated()
                                .pathMatchers("/eazybank/loans/**").authenticated()
                                .pathMatchers("/eazybank/cards/**").authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));
        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());

        return serverHttpSecurity.build();
    }
}