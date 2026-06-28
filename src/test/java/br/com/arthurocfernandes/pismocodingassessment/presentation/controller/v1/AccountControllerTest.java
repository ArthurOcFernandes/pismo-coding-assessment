package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.CreateAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.AccountService;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.AccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController controller;

    @Test
    void shouldReturnCreatedWhenCreateAccountSucceeds() {
        ReadAccountDto expectedDto = new ReadAccountDto("12345678900", 1L);
        when(accountService.createAccount(anyString())).thenReturn(expectedDto);

        ResponseEntity<ReadAccountDto> response = controller.createAccount(new CreateAccountDto("12345678900"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    void shouldReturnOkWhenGetAccountSucceeds() {
        ReadAccountDto expectedDto = new ReadAccountDto("12345678900", 1L);
        when(accountService.getAccount(anyLong())).thenReturn(expectedDto);

        ResponseEntity<ReadAccountDto> response = controller.getAccount(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenGetAccountFails() {
        when(accountService.getAccount(anyLong())).thenThrow(new AccountNotFoundException());

        assertThrows(AccountNotFoundException.class, () ->
                controller.getAccount(1L));
    }
}