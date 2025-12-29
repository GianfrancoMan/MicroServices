package com.eazybytes.accounts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Questa eccezione si verifica quando un utente prova a registrarsi con un numero di telefono già registrato e quindi già presente nel DB
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)//nella risposta verrà inserito questo status
public class CustomerAlreadyExistException extends RuntimeException {
    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
