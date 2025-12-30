package com.eazybytes.accounts.exception;

import com.eazybytes.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice //Ogni eccezione che si verifica nel controller verrà gestita tramite questa classe
public class GlobalExceptionhandler {

    //Questo metodo gestisce l'eccezione di tipo CustomerAlreadyExistException
    @ExceptionHandler(value = CustomerAlreadyExistException.class)//sto dicendo a spring che questo metodo gestisce le ecezioni di tipo CustomerAlreadyExistException
    public ResponseEntity<ErrorResponseDto> handleCustomerAlreadyExistException(
            CustomerAlreadyExistException exception,
            WebRequest webRequest) { //N.B WebRequest è fornito dal contesto Spring e rappresenta la richiesta che ha generato leccezione

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity< ErrorResponseDto>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    //Questo metodo gestisce l'eccezione di tipo ResourceNotFoundException
    @ExceptionHandler(value =ResourceNotFoundException.class)//sto dicendo a spring che questo metodo gestisce le ecezioni di tipo CustomerAlreadyExistException
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest) { //N.B WebRequest è fornito dal contesto Spring e rappresenta la richiesta che ha generato leccezione

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<ErrorResponseDto>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}
