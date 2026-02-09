package com.eazybytes.gatewayserver.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(1) //definisce l'ordine di esecuzione dei fintri che andiamo a definire, questo è oreder 1 quindi il primo da eseguire.
@Component
public class RequestTraceFilter implements GlobalFilter { //GlobalFilter è un'interfaccia che permette di eseguire un filtro globalmente per tutti i request.

    private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

    @Autowired
    FilterUtility filterUtility;

    @Override // Iimplementazione del metodo di GlobalFilter
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        /*ServerWebExchange è un oggetto che rappresenta la richiesta e la risposta HTTP(reactive project)
        * GatewayFilterChain è un oggetto che rappresenta la catena di filtri da eseguire */
        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();//recupera gli headers della richiesta
        // invoca il metodo isCorrelationIdPresent per verificare se esiste il correlation id
        if (isCorrelationIdPresent(requestHeaders)) {
            logger.debug("eazyBank-correlation-id found in RequestTraceFilter : {}",
                    filterUtility.getCorrelationId(requestHeaders));//invoca il metodo getCorrelationId per ottenere il correlation id
        } else {
            //invoca il metodo generateCorrelationId per generare un correlation id
            String correlationID = generateCorrelationId();
            exchange = filterUtility.setCorrelationId(exchange, correlationID);
            logger.debug("eazyBank-correlation-id generated in RequestTraceFilter : {}", correlationID);
        }
        return chain.filter(exchange); //invoca il metodo filter per eseguire il filtro
    }

    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        if (filterUtility.getCorrelationId(requestHeaders) != null) {
            return true;
        } else {
            return false;
        }
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }

}
