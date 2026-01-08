package com.eazybytes.loans.controller;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.ErrorResponseDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag( //consente di dare dettagli su questo controller nella documentazione swagger
        name = "CRUD REST APIs for Loans microservice in EazyBank",
        description = "CRUD REST APIs  to CREATE, UPDATE, FETCH AND DELETE Loans details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController {

    ILoansService iLoansService;

    /**
     *Creates and stores in the DB a new Loans instance, based on the mobile number
     *
     * @param mobileNumber the mobile number of who want the loan
     * @return  an instance of ResponseEntity<ResponseDto> if the operation success
     */
    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside EazyBank"
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
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> ctreateLoan(@RequestParam
                                                                                    @NotEmpty
                                                                                    @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile Number must be 10 digits")
                                                                                    String mobileNumber) {

        iLoansService.createLoan(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }


    /**
     * Fetches the specific loan from the DB using the mobile number of as a param request
     *
     * @param mobileNumber the mobile number
     * @return an instance of ResponseEntity<LoansDto> with loan details.
     */
    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch  Loan details  based on a mobile number"
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
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam
                                                                                        @NotEmpty
                                                                                        @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile Number must be 10 digits")
                                                                                        String mobileNumber) {

        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity.ok().body(loansDto);
    }

    /**
     * Updates the specific loan from the DB using the loan number of as a param request
     *
     * @param loansDto the loan details
     * @return an instance of ResponseEntity<ResponseDto> if the operation success
     */
    @Operation(
            summary = "update loan Details REST API",
            description = "REST API to update Loan details based on a loan number"
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
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoansDto loansDto) {

        boolean updated = iLoansService.updateLoan(loansDto);
        if(updated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to Delete Loan details based on a mobile number"
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
    public ResponseEntity<ResponseDto> deleteLoanDetails(
                                                                @RequestParam
                                                                @NotEmpty
                                                                @Pattern(regexp="(^[0-9]{10}$)",message = "Mobile Number must be 10 digits")
                                                                String mobileNumber) {
        boolean deleted = iLoansService.deleteLoan(mobileNumber);
        if(deleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
        }
    }

}
