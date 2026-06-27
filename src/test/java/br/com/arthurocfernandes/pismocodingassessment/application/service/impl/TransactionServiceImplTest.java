package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.ReadTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Transaction;
import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.TransactionRepository;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Test
    void shouldFailWhenAccountDoesNotExist() {
        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Result<?> result = transactionServiceImpl.CreateTransaction(1L, 1, new BigDecimal(1000));

        assertFalse(result.isSuccess());
        assertEquals("ACCOUNT_NOT_FOUND", result.getError().code());
        assertEquals("A valid account must be provided", result.getError().message());
        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, result.getError().status());
        verify(accountRepository, times(1)).findById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldFailWhenInvalidOperationId() {
        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(new CustomerAccount()));

        Result<?> result = transactionServiceImpl.CreateTransaction(1L, -99, new BigDecimal(1000));

        assertFalse(result.isSuccess());
        assertEquals("INVALID_OPERATION_TYPE", result.getError().code());
        assertEquals("A valid operation must be provided", result.getError().message());
        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, result.getError().status());
        verify(accountRepository, times(1)).findById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldSucceedWhenValidInput() {
        var transaction = new Transaction(
                1L,
                new CustomerAccount(1L, "1234", LocalDateTime.now()),
                OperationType.PURCHASE,
                new BigDecimal(1000),
                LocalDateTime.now());

        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(new CustomerAccount()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Result<?> result = transactionServiceImpl.CreateTransaction(1L, OperationType.PURCHASE.getValue(), new BigDecimal(1000));

        assertTrue(result.isSuccess());
        assertEquals(result.getValue(), new ReadTransactionDto(transaction));
        verify(accountRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(any());
    }
}
