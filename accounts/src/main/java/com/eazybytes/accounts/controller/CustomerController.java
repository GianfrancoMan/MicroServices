package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag( //consente di dare dettagli su questo controller nella documentazione swagger
        name = "REST APIs for Customer in EazyBank",
        description = "REST APIs  in EazyBank to to fetch customer details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private final ICustomerService iCustomerService;

    @Autowired
    public CustomerController(ICustomerService iCustomerService) {
        this.iCustomerService = iCustomerService;
    }

    /**
     * Fetch Customer Details REST API
     *
     * @param mobileNumber the mobile number of the customer to fetch details for
     * @return ResponseEntity containing CustomerDetailsDto with whole customer details comprehensive of loans and cards details
     */
    @Operation(
            summary = "Fetch Customer Details REST API",
            description = "REST API to fetch  whole Customer details  based on a mobile number"
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
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits") //Non avendo in input un oggetto ma un valore singolo, la validazione si fa con la validation constraint @Pattern
            @RequestParam
            String mobileNumber) {

        CustomerDetailsDto customerDetailsDto = iCustomerService.fetchCustomerDetails(mobileNumber);

        return ResponseEntity.ok().body(customerDetailsDto);
    }

}
