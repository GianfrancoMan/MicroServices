package com.eazybytes.loans.controller;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoansService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
