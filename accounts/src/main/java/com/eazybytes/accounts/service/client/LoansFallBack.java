package com.eazybytes.accounts.service.client;

import com.eazybytes.accounts.dto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*Questa classe agisce da fallback per il client openfeign del ms loans */
@Component
public class LoansFallBack implements LoansFeignClient {

    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String correlationId, String mobileNumber) {
        return null; //in caso di malfunzioamento di loans il client openfeign non riceve una Exception ma un valore null.
    }

}
