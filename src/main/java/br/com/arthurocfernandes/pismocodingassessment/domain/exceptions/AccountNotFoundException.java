package br.com.arthurocfernandes.pismocodingassessment.domain.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends DomainException {
    public AccountNotFoundException() {
        super("ACCOUNT_NOT_FOUND", "A valid account must be provided", HttpStatus.NOT_FOUND);
    }
}