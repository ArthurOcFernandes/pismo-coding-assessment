package br.com.arthurocfernandes.pismocodingassessment.domain.exceptions;

import org.springframework.http.HttpStatus;

public class DocumentNumberRequiredException extends DomainException {
    public DocumentNumberRequiredException() {
        super("DOCUMENT_NUMBER_REQUIRED", "Document number must be provided", HttpStatus.UNPROCESSABLE_CONTENT);
    }
}