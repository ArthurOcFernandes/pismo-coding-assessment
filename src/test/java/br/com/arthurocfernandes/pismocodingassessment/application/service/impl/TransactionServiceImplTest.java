package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.CreateTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.ReadTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Account;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Transaction;
import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.AccountNotFoundException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.InvalidOperationTypeException;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() {
        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () ->
                transactionServiceImpl.CreateTransaction(
                        new CreateTransactionDto(1L, 1, new BigDecimal(1000))));

        verify(accountRepository, times(1)).findById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowInvalidOperationTypeExceptionWhenInvalidOperationId() {
        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(new Account()));

        assertThrows(InvalidOperationTypeException.class, () ->
                transactionServiceImpl.CreateTransaction(
                        new CreateTransactionDto(1L, -99, new BigDecimal(1000))));

        verify(accountRepository, times(1)).findById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldReturnReadTransactionDtoWhenValidInput() {
        var transaction = new Transaction(
                1L,
                new Account(1L, "1234", LocalDateTime.now()),
                OperationType.PURCHASE,
                new BigDecimal(1000),
                LocalDateTime.now());

        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.of(new Account()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        ReadTransactionDto result = transactionServiceImpl.CreateTransaction(
                new CreateTransactionDto(1L, OperationType.PURCHASE.getValue(), new BigDecimal(1000)));

        assertNotNull(result);
        assertEquals(new ReadTransactionDto(transaction), result);
        verify(accountRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(any());
    }
}