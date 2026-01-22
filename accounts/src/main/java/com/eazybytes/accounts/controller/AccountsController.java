package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.costants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag( //consente di dare dettagli su questo controller nella documentazione swagger
        name = "CRUD REST APIs for Accounts microservice in EazyBank",
        description = "CRUD REST APIs  in EazyBank to CREATE, UPDATE, FETCH AND DELETE accounts details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
//@AllArgsConstructor//se è presente un solo costruttore questa annotazione mi permetterà di iniettare tutti gli attributi privati di quessta classe senza scrivere il costruttore
@Validated //Avverte Spring che ci sono delle valisazioni da fare nei dati che arrivano agli endpoints di questo controller
public class AccountsController {

    private final IAccountsService iAccountsService;//verra iniettato tramite l'annotazione Lombok AllArgsConstructor, come se avessi scritto il costruttore con l'annotazione @Autowired

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Autowired//optional when we have only one constructor
    public AccountsController(IAccountsService iAccountsService) {
        this.iAccountsService = iAccountsService;
    }

    /**
     * Creates a new account
     * @param customerDto - CoustumerDto object
     */
    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account inside EazyBank"
    )
    @ApiResponses({//consente di inserire dettagli sulla responce nella documentazione swagger
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")//Quando sicrea un nuovo record si invia una POST request quindi usiamo l'annotazione @PostMapping
    public ResponseEntity<ResponseDto> createAccount(
            @Valid //@Valid indica che Spring deve fare le validazioni sull'ogetto CustomerDto passato come parametro.
            @RequestBody //@RequestBody indica che la richiesta ha un body con i dati al suo interno, è neccessario creare una classe che abbia le stesse caratteristichedel body affinchè Spring possa mappare i valori del body con i valori della classe
            CustomerDto customerDto) {
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
    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch  Customer & Account details  based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
/*    @ApiResponse( //consente di inserire dettagli sulla responce nella documentazione swagger
            responseCode = "200",
            description = "HTTP Status OK"
    )*/
    @GetMapping("/fetch")//quando si cerca di leggere un dato si invia una GET request quindi usiamo l'annotazione @GetMapping
    public ResponseEntity<CustomerDto> fetchAccountDetails(
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") //Non avendo in input un oggetto ma un valore singolo, la validazione si fa con la validation constraint @Pattern
            @RequestParam
            String mobileNumber) {
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
    @Operation(
            summary = "update Account Details REST API",
            description = "REST API to update Customer & Account details based on a account number"
    )
    @ApiResponses({ //consente di inserire dettagli su più  responses nella documentazione swagger
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ) ,
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed"
            ) ,
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR",
                    content = @Content( //i dati delle response in errore sono indicati nella classe ErrorResponseDto
                                                        // che pero' viene invocata nella GlobalEceptionhandler mentre  qui non viene vista ...
                            schema = @Schema( //pertanto dobbiamo darne indicazione con queste annotazioni @Content e @Schema
                                    implementation = ErrorResponseDto.class
                            )
                    )
            )
    })
    @PutMapping("/update")
    public  ResponseEntity<ResponseDto> updateAccountDetails(@Valid@RequestBody CustomerDto customerDto) {
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
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    /**
     * Deletes the account details of a customer
     * @param mobileNumber - Mobile number of the customer
     * @return - boolean
     */
    @Operation(
            summary = "Delete Account Details REST API",
            description = "REST API to Delete Customer & Account details based on a mobile number"
    )
    @ApiResponses({ //consente di inserire dettagli su più  responses nella documentazione swagger
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ) ,
            @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation Failed"
            ) ,
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status INTERNAL SERVER ERROR"
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
            @RequestParam
            String mobileNumber) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity.ok(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Get build information",
            description = "Get build information that is deployed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    /**
     * Get properties of the Accounts microservice by field marked with @Value annotation
     */
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get java version",
            description = "Get java version that is installed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    /**
     *  Get properties of the Accounts microservice by the Environment instance Injected from the spring context
     */
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get contact info",
            description = "Get contact info about the developer team that can be reached in case of any issues."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    /**
     * Get properties of the Accounts microservice leveraging on Pojo Class AccountsContactInfoDto
     * that is marked with the @ConfigurationProperties annotation used to externalize configurations
     * (to work is required to mark with the @EnableConfigurationProperties the main class of the Accounts microservice)
     */
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(accountsContactInfoDto);
    }
}
