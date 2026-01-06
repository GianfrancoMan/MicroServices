package com.eazybytes.loans.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResorceNotFoundException extends RuntimeException {
    public ResorceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s = %s not found", resourceName, fieldName, fieldValue));
    }
}
