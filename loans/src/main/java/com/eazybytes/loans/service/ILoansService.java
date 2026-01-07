package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public interface ILoansService {

    /**
     * Create a new loan for the given mobile number
     * The loan will be created in the database only if the mobile number is not already present
     *
     * @param mobileNumber the mobile number of the customer that gets the loan
     */
    void createLoan(String mobileNumber );

    /**
     * Fetches the loan based on the mobile number
     *
     * @param mobileNumber String, the mobile number used to create a loan
     * @return LoansDto object that matches the given mobile number
     */
    LoansDto fetchLoan(String mobileNumber);

    boolean updateLoan(LoansDto loansDto);

    boolean deleteLoan(String mobileNumber);
}
