package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;

public interface ILoansService {

    /**
     * Create a new loan for the given mobile number
     * The loan will be created in the database only if the mobile number is not already present
     *
     * @param mobileNumber the mobile number of the customer that gets the loan
     */
    void createLoan(String mobileNumber );

}
