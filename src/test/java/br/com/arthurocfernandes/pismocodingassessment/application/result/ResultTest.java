package br.com.arthurocfernandes.pismocodingassessment.application.result;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultTest {

    @Test
    void shouldCreateSuccessResult() {
        Result<String> result = Result.success("ok");

        assertTrue(result.isSuccess());
        assertEquals("ok", result.getValue());
    }

    @Test
    void shouldCreateFailureResult() {
        Result<String> result = Result.failure(new OperationError("TEST", "problem", HttpStatus.BAD_REQUEST));

        assertFalse(result.isSuccess());
        assertEquals("TEST", result.getError().code());
        assertThrows(IllegalStateException.class, result::getValue);
    }
}

