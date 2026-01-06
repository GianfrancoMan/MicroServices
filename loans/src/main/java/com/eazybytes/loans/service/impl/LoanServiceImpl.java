package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResorceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoansService {

    private LoansRepository loansRepository;

    /**
     * Create a new loan for the given mobile number
     * The loan will be created in the database only if the mobile number is not already present
     *
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber)  {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(! optionalLoans.isPresent()) {
            loansRepository.save(createNewLoans(mobileNumber));
        } else {
            throw new LoanAlreadyExistsException(String.format("Loan with mobile numeber %s already exists", mobileNumber));
        }
    }

    /**
     * Create a new Loan object providing it a consistent loan number.
     *
     * @return  the new Loan object
     */
    private Loans createNewLoans(String mobileNumber) {

        Loans newLoan = new Loans();

        Long randomLoanNumber = 100000000000L + new Random().nextInt(900000000) ;
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setTotalLoan(LoansConstants.NEW_LOAN_LIMIT);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        return newLoan;
    }


    /**
     * Fetches the loan based on the mobile number
     *
     * @param mobileNumber String, the mobile number used to create a loan
     * @return LoansDto object that matches the given mobile number
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResorceNotFoundException("Loan", "mobileNumber", mobileNumber)
        );

        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }
}
