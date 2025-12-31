package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema( //permette di dare dettagli sulle classi usate negli endpoint da visualizzare nella documentazione swagger
        name = "Customer", // anziche visualizzare il nome tecnico "CustomerDto" verrà visualizzato "Customer"
        description = "Schema to hold Customer & Account information"
)
public class CustomerDto {
    @Schema(description = "Name of the customer", example = "John Doe")
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name must be between 5 and 30")
    private String name;

    @Schema(description = "Email address of the customer", example = "johndoe@gmail.com")
    @NotEmpty(message = "Email address cannot be null orempty")
    @Email(message = "Email address should be a valid email")
    private String email;

    @Schema(description = "Mobile number of the customer", example = "3331234323")
    @Pattern(regexp = "(^[0-9]{10})$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountsDto accountsDto;
}
