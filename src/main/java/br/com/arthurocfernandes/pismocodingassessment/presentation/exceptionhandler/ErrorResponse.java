package br.com.arthurocfernandes.pismocodingassessment.presentation.exceptionhandler;

import java.time.LocalDateTime;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String code,
        String message
) {}