package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*Questa classe agisce da fallback per il client openfeign del ms cards */
@Component
public class CardsFallBack implements CardsFeignClient {
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String correlationId, String mobileNumber) {
        return null; //in caso di malfunzioamento di cards il client openfeign non riceve una Exception ma un valore null.
    }
}
