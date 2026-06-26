package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.CreateAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.OperationError;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.application.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController controller;

    @Test
    void shouldReturnCreatedWhenServiceSucceeds() {
        when(accountService.createAccount(anyString()))
                .thenReturn(Result.success(new ReadAccountDto("12345678900", 1L)));

        ResponseEntity<?> response = controller.createAccount(new CreateAccountDto("12345678900"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(ReadAccountDto.class, response.getBody());
        assertEquals("12345678900", ((ReadAccountDto) response.getBody()).documentNumber());
    }

    @Test
    void shouldReturnErrorResponseWhenServiceFails() {
        when(accountService.createAccount(anyString()))
                .thenReturn(Result.failure(new OperationError(
                        "DOCUMENT_NUMBER_REQUIRED",
                        "documentNumber must be provided",
                        HttpStatus.BAD_REQUEST
                )));

        ResponseEntity<?> response = controller.createAccount(new CreateAccountDto(""));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(OperationError.class, response.getBody());
        assertTrue(((OperationError) response.getBody()).message().contains("documentNumber"));
    }
}

