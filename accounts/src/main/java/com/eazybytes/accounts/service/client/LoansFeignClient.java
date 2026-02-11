package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

//Indica che un'interfaccia dichiarativa che agisce come CLIENT FEIGN che si connette con il microservizio LOANS
@FeignClient(name = "loans", fallback = LoansFallBack.class)
public interface LoansFeignClient {

    //Ogni metodo astrato dichiarato in questa interfaccia deve avere un corrispndente metodo
    //nel controller del servizio al quale ci stiamo connettendo, in questo caso nel LoansController

    @GetMapping(value = "/api/fetch", consumes = "application/json")
    public ResponseEntity<LoansDto> fetchLoanDetails(
            @RequestHeader(value = "eazybank-correlation-id") String correlationId,
            @RequestParam String mobileNumber);
}










