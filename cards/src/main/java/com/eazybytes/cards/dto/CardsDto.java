package com.eazybytes.cards.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CardsDto {

    @NotEmpty(message = "Mobile number cannot be empty or null")
    @Pattern(regexp = "(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotEmpty(message = "Card number cannot be empty or null")
    @Pattern(regexp = "(^[0-9]{12}$)", message = "Card number must be 12 digits")
    private String cardNumber;

    @NotEmpty(message = "Card type cannot be empty or null")
    private String cardType;

    @Positive(message = "Total amount limit must be grater than 0")
    private int totalLimit;

    @PositiveOrZero(message = "Total amount used must be grater than or equal to 0")
    private int amountUsed;

    @PositiveOrZero(message = "Total available amount must be grater than or equal to 0")
    private int availableAmount;
}
