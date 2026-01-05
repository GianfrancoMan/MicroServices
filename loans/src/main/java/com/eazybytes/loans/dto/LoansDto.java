package com.eazybytes.loans.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class LoansDto {

    @NotEmpty(message = "Mobile number cannot be empty or null")
    @Pattern(regexp="(^|[0-9]{10}$)",message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Mobile number cannot be empty or null")
    @Pattern(regexp="(^|[0-9]{12}$)",message = "Mobile Number must be 12 digits")
    private String loanNumber;

    @NotEmpty(message = "Mobile number cannot be empty or null")
    private String loanType;

    @Positive(message = "Total loan amount must be grater than 0")
    private int totalLoan;

    @PositiveOrZero(message = "Total loan amount paid must be grater than or equal to 0")
    private int amountPaid;

    @PositiveOrZero(message = "Total  outstanding amount must be grater than or equal to 0")
    private int outstandingAmount;

}
