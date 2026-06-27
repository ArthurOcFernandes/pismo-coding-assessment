package br.com.arthurocfernandes.pismocodingassessment.domain.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidOperationException  extends DomainException {
    public InvalidOperationException() {
        super("INVALID_OPERATION", "Invalid amount for operation", HttpStatus.BAD_REQUEST);
    }
}
