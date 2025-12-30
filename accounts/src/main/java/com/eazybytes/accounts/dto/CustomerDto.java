package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerDto {
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name must be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email address cannot be null orempty")
    @Email(message = "Email address should be a valid email")
    private String email;

    @Pattern(regexp = "(^[0-9]{10})$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
