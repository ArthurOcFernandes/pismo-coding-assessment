package br.com.arthurocfernandes.pismocodingassessment.application.result;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public record OperationError(String code, String message, HttpStatus status) {
    public OperationError {
        Objects.requireNonNull(code, "code must be provided");
        Objects.requireNonNull(message, "message must be provided");
        Objects.requireNonNull(status, "status must be provided");
    }
}

