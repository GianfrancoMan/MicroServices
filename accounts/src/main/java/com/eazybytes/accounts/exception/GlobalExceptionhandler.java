package com.eazybytes.accounts.exception;

import com.eazybytes.accounts.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Gestisce le eccezioni che si verificano nel controller
 * Estende la classe ResponseEntityExceptionHandler per gestire le eccezioni che si verificano quando le validazioni non vengono rispettate,
 * questo avviene tramite l'override del metodo handleMethodArgumentNotValid
 */
@ControllerAdvice //Ogni eccezione che si verifica nel controller verrà gestita tramite questa classe
public class GlobalExceptionhandler extends ResponseEntityExceptionHandler {

    //Questo metodo gestisce l'eccezione di tipo ResourceNotFoundException
    @ExceptionHandler(value =Exception.class)//sto dicendo a spring che questo metodo gestisce le ecezioni di tipo CustomerAlreadyExistException
    public ResponseEntity<ErrorResponseDto> handleGlobalException(
            Exception exception,
            WebRequest webRequest) { //N.B WebRequest è fornito dal contesto Spring e rappresenta la richiesta che ha generato leccezione

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                webRequest.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<ErrorResponseDto>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

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

    @Override
    protected  ResponseEntity<Object>  handleMethodArgumentNotValid(  MethodArgumentNotValidException ex,
                                                                                                                        HttpHeaders headers,
                                                                                                                        HttpStatusCode status,
                                                                                                                        WebRequest request
                                                                                ) {
        Map<String, String> validationErrors = new HashMap<>(); //conterrà tutti gli errori di validazione
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();//otteniamo tutti gli errori di validazione
        validationErrorList.forEach(error -> { //itera la lista degli errori di validazione per..
            String fieldName = ((FieldError) error).getField();//ottenere il nome del campo che ha generato l'errore..
            String validationMsg = error.getDefaultMessage();// e il messaggio di vaalidazione che abbiamo specificato...
            validationErrors.put(fieldName, validationMsg);//aggiunge alla Mappa questi due stringhe sotto forma di coppia chiave-valore
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST); //restituisce nella risposta la lista degli errori di validazione sottoforma di Mappa con coppia chiave-valore(campo-messaggio di validazione)
    }
}
