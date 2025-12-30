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

    private IAccountsService iAccountsService;//verra iniettato tramite l'annotazione Lombok AllArgsConstructor, come se avessi scritto il costruttore con l'annotazione @Autowired

    /**
     * Creates a new account
     * @param customerDto - CoustumerDto object
     */
    @PostMapping("/create")//Quando sicrea un nuovo record si invia una POST request quindi usiamo l'annotazione @PostMapping
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {//@RequestBody indica che la richiesta ha un body con i dati al suo interno, è neccessario creare una classe che abbia le stesse caratteristichedel body affinchè Spring possa mappare i valori del body con i valori della classe
        iAccountsService.createAccount(customerDto);//se non c'è ecezione verrà inviata la risposta sotto, altrimenti la risposta sara gestita nella GlobalExceptionHandler
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    /**
     * Fetches the account details of a customer
     * @param mobileNumber - Mobile number of the customer
     * @return - CustomerDto object
     */
    @GetMapping("/fetch")//quando si cerca di leggere un dato si invia una GET request quindi usiamo l'annotazione @GetMapping
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        //@RequestParam indica che la richiesta ha un parametro al suo interno, possiamo dare un valore diverso al parametro, ma in questo caso si deve assegnare alla proprietà value il nome del parametro dell'endpoint e assegnare al parametro del metodo il nome desiderato

        CustomerDto customerDto = iAccountsService.fetchAccountDetails(mobileNumber);
        //return ResponseEntity.status(HttpStatus.OK).body(customerDto); o anche...
        return  ResponseEntity.ok(customerDto);
    }

    /**
     * Updates the account details of a customer
     * @param customerDto - CoustumerDto object
     * @return - boolean
     */
    @PutMapping("/update")
    public  ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated) {
            /*return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200)); oppure ...
                    */
            return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            /*return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500)); oppure ...
                    */
            return ResponseEntity.internalServerError().body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }

    /**
     * Deletes the account details of a customer
     * @param mobileNumber - Mobile number of the customer
     * @return - boolean
     */
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.internalServerError().body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }


}
