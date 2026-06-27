package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.CreateTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.ReadTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.TransactionService;
import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.AccountNotFoundException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.InvalidOperationTypeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void shouldReturnCreatedWhenCreateTransactionSucceeds() {
        CreateTransactionDto createTransactionDto = new CreateTransactionDto(1L, 1, new BigDecimal("100.00"));
        ReadTransactionDto expectedDto = new ReadTransactionDto(1L, 1L, OperationType.PURCHASE, new BigDecimal("100.00"));

        when(transactionService.CreateTransaction(any(CreateTransactionDto.class)))
                .thenReturn(expectedDto);

        ResponseEntity<ReadTransactionDto> response = transactionController.createTransaction(createTransactionDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedDto, response.getBody());
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenCreateTransactionFailsWithAccountNotFound() {
        CreateTransactionDto createTransactionDto = new CreateTransactionDto(1L, 1, new BigDecimal("100.00"));

        when(transactionService.CreateTransaction(any(CreateTransactionDto.class)))
                .thenThrow(new AccountNotFoundException());

        assertThrows(AccountNotFoundException.class, () ->
                transactionController.createTransaction(createTransactionDto));
    }

    @Test
    void shouldThrowInvalidOperationTypeExceptionWhenCreateTransactionFailsWithInvalidOperationType() {
        CreateTransactionDto createTransactionDto = new CreateTransactionDto(1L, 99, new BigDecimal("100.00"));

        when(transactionService.CreateTransaction(any(CreateTransactionDto.class)))
                .thenThrow(new InvalidOperationTypeException());

        assertThrows(InvalidOperationTypeException.class, () ->
                transactionController.createTransaction(createTransactionDto));
    }
}