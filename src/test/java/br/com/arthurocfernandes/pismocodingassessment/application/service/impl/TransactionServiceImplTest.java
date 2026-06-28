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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    @Test
    void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> transactionServiceImpl.CreateTransaction(
                        new CreateTransactionDto(
                                1L,
                                OperationType.PURCHASE.getValue(),
                                new BigDecimal("1000"))));

        verify(accountRepository).findById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldThrowInvalidOperationTypeExceptionWhenOperationTypeDoesNotExist() {
        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(new Account()));

        assertThrows(InvalidOperationTypeException.class,
                () -> transactionServiceImpl.CreateTransaction(
                        new CreateTransactionDto(
                                1L,
                                -99,
                                new BigDecimal("1000"))));

        verify(accountRepository).findById(1L);
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldCreateTransaction() {
        var account = new Account();
        account.setId(1L);

        var transaction = new Transaction();
        transaction.setTransactionId(1L);
        transaction.setAccount(account);
        transaction.setOperationType(OperationType.PURCHASE);
        transaction.setAmount(new BigDecimal("-1000"));
        transaction.setCreatedAt(LocalDateTime.now());

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        ReadTransactionDto result = transactionServiceImpl.CreateTransaction(
                new CreateTransactionDto(
                        1L,
                        OperationType.PURCHASE.getValue(),
                        new BigDecimal("1000")));

        assertNotNull(result);
        assertEquals(new ReadTransactionDto(transaction), result);

        verify(accountRepository).findById(1L);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldPersistTransactionWithMappedData() {
        var account = new Account();
        account.setId(1L);

        when(accountRepository.findById(1L))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        transactionServiceImpl.CreateTransaction(
                new CreateTransactionDto(
                        1L,
                        OperationType.PURCHASE.getValue(),
                        new BigDecimal("1000")));

        verify(transactionRepository).save(transactionCaptor.capture());

        Transaction persisted = transactionCaptor.getValue();

        assertEquals(account, persisted.getAccount());
        assertEquals(OperationType.PURCHASE, persisted.getOperationType());
        assertNotNull(persisted.getCreatedAt());
    }
}