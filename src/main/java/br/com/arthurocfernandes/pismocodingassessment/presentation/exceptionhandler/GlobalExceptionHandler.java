package br.com.arthurocfernandes.pismocodingassessment.presentation.exceptionhandler;

import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.DomainException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(ZoneOffset.UTC),
                ex.getHttpStatus().value(),
                ex.getHttpStatus().getReasonPhrase(),
                ex.getCode(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}