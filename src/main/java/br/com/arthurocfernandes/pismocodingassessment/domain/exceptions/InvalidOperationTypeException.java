package br.com.arthurocfernandes.pismocodingassessment.domain.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidOperationTypeException extends DomainException {
    public InvalidOperationTypeException() {
        super("INVALID_OPERATION_TYPE", "A valid operation must be provided", HttpStatus.UNPROCESSABLE_CONTENT);
    }
}