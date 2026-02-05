package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Schema( //permette di dare dettagli sulle classi usate negli endpoint da visualizzare nella documentazione swagger
        name = "Loans", // anziche visualizzare il nome tecnico "AccountsDto" verrà visualizzato "Accounts"
        description = "Schema to hold Loans information"
)
@Data
public class LoansDto {

    @Schema(description = "Mobile Number of the Eazy Bank customer that owns the loan", example = "1902783691")
    @NotEmpty(message = "Mobile number cannot be empty or null")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Loan Number of Eazy Bank loan", example = "100902783691")
    @NotEmpty(message = "Loan number cannot be empty or null")
    @Pattern(regexp="(^$|[0-9]{12})",message = "Mobile Number must be 12 digits")
    private String loanNumber;

    @Schema(description = "Loan type of Eazy Bank loan", example = "Home Loan")
    @NotEmpty(message = "loan type cannot be empty or null")
    private String loanType;

    @Schema(description = "The total amount of an Eazy Bank loan", example = "100000")
    @Positive(message = "Total loan amount must be grater than 0")
    private int totalLoan;

    @Schema(description = "The total amount paid of a Eazy Bank loan", example = "10000")
    @PositiveOrZero(message = "Total loan paid must be grater than or equal to 0")
    private int amountPaid;

    @Schema(description = "Total outstanding amount of an Eazy Bank loan", example = "90000")
    @PositiveOrZero(message = "Total outstanding amount must be grater than or equal to 0")
    private int outstandingAmount;

}
