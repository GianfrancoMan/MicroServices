package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.CardsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//Indica che un'interfaccia dichiarativa che agisce come CLIENT FEIGN che si connette con il microservizio CARDS
@FeignClient(value = "cards")
public interface CardsFeignClient {

    //Ogni metodo astrato dichiarato in questa interfaccia deve avere un corrispndente metodo
    //nel controller del servizio al quale ci stiamo connettendo, in questo caso nel CardsController

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}










