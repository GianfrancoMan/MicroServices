package com.eazybytes.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/*All @Schema annotations are relative to Swagger OPEN API*/
@Schema(name = "CardsDto", description = "Schema to hold Card information")
@Data
public class CardsDto {

    @Schema(description = "Mobile Number of the costumer", example = "3446301692")
    @NotEmpty(message = "Mobile number cannot be empty or null")
    @Pattern(regexp = "(^[0-9]{10}$)", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Card Number of the costumer", example = "100039487267")
    @NotEmpty(message = "Card number cannot be empty or null")
    @Pattern(regexp = "(^[0-9]{12}$)", message = "Card number must be 12 digits")
    private String cardNumber;

    @Schema(description = "Type of the Card", example = "Credit Card")
    @NotEmpty(message = "Card type cannot be empty or null")
    private String cardType;

    @Schema(description = "Total amount limit available against a card", example = "100000")
    @Positive(message = "Total amount limit must be grater than 0")
    private int totalLimit;

    @Schema(description = "Total amount used by a Customer", example = "1000")
    @PositiveOrZero(message = "Total amount used must be grater than or equal to 0")
    private int amountUsed;

    @Schema(description = "Total available amount against a card", example = "90000")
    @PositiveOrZero(message = "Total available amount must be grater than or equal to 0")
    private int availableAmount;
}
