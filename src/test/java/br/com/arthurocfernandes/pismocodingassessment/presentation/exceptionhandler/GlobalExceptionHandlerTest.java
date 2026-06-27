package br.com.arthurocfernandes.pismocodingassessment.presentation.exceptionhandler;

import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.AccountNotFoundException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.DocumentNumberRequiredException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.InvalidOperationException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.InvalidOperationTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleAccountNotFoundException() {
        AccountNotFoundException exception = new AccountNotFoundException();

        ResponseEntity<ErrorResponse> response =
                handler.handleDomainException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ErrorResponse body = response.getBody();

        assert body != null;
        assertEquals("ACCOUNT_NOT_FOUND", body.code());
        assertEquals("A valid account must be provided", body.message());
    }

    @Test
    void shouldHandlerDocumentNumberRequiredException(){
        DocumentNumberRequiredException exception = new DocumentNumberRequiredException();

        ResponseEntity<ErrorResponse> response =
                handler.handleDomainException(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, response.getStatusCode());

        ErrorResponse body = response.getBody();

        assert body != null;
        assertEquals("DOCUMENT_NUMBER_REQUIRED", body.code());
        assertEquals("Document number must be provided", body.message());
    }

    @Test
    void shouldHandleInvalidOperationTypeException() {
        InvalidOperationTypeException exception = new InvalidOperationTypeException();

        ResponseEntity<ErrorResponse> response =
                handler.handleDomainException(exception);

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, response.getStatusCode());

        ErrorResponse body = response.getBody();

        assert body != null;
        assertEquals("INVALID_OPERATION_TYPE", body.code());
        assertEquals("A valid operation must be provided", body.message());
    }

    @Test
    void shouldHandleInvalidOperationException() {
        InvalidOperationException exception = new InvalidOperationException();

        ResponseEntity<ErrorResponse> response =
                handler.handleDomainException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse body = response.getBody();

        assert body != null;
        assertEquals("INVALID_OPERATION", body.code());
        assertEquals("Invalid amount for operation", body.message());
    }
}
