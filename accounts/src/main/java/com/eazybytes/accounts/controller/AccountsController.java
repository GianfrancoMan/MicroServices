package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.costants.AccountsConstants;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor //se è presente un solo costruttore questa annotazione mi permetterà di iniettare tutti gli attributi privati di quessta classe senza scrivere il costruttore
public class AccountsController {

    private IAccountsService accountsService;//verra iniettato tramite l'annotazione Lombok AllArgsConstructor, come se avessi scritto il costruttore con l'annotazione @Autowired

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);//se non c'è ecezione verrà inviata la risposta sotto, altrimenti la risposta sara gestita nella GlobalExceptionHandler
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));

    }

}
