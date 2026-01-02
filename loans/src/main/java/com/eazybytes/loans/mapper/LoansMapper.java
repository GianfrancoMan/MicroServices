package com.eazybytes.loans.mapper;

import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;

/**
 * A mapper to form a LoansDto object from a Loans object and vice versa
 */
public class LoansMapper {

    /**
     * Form a Consistent LoansDto object based on the given Loans object
     *
     * @param loans the formed Loans Instance
     * @param loansDto the empty LoansDto instance that will be filled using the state of the given Loans instance
     * @return a LoansDto Object
     */
    public static LoansDto mapToLoansDto(Loans loans, LoansDto loansDto) {
        loansDto.setMobileNumber(loans.getMobileNumber());
        loansDto.setLoanNumber(loans.getLoanNumber());
        loansDto.setLoanType(loans.getLoanType());
        loansDto.setTotalLoan(loans.getTotalLoan());
        loansDto.setAmountPaid(loans.getAmountPaid());
        loansDto.setOutstandingAmount(loans.getOutstandingAmount());

        return loansDto;
    }

    /**
     * Form a Consistent Loans object based on the given LoansDto object
     *
     * @param loansDto the formed LoansDto Instance
     * @param loans the empty Loans instance that will be filled using the state of the given LoansDto instance
     * @return a Loans Object
     */
    public static Loans mapToLoans(LoansDto loansDto, Loans loans) {
        loans.setMobileNumber(loansDto.getMobileNumber());
        loans.setLoanNumber(loansDto.getLoanNumber());
        loans.setLoanType(loansDto.getLoanType());
        loans.setTotalLoan(loansDto.getTotalLoan());
        loans.setAmountPaid(loansDto.getAmountPaid());
        loans.setOutstandingAmount(loansDto.getOutstandingAmount());

        return loans;
    }

}
