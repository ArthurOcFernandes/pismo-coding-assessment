package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Account;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.AccountNotFoundException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.DocumentNumberRequiredException;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl service;

    @Captor
    private ArgumentCaptor<Account> customerAccountCaptor;

    @Test
    void shouldThrowDocumentNumberRequiredExceptionWhenDocumentNumberIsMissing() {
        assertThrows(DocumentNumberRequiredException.class, () -> service.createAccount("   "));

        verify(accountRepository, never()).findByDocumentNumber(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldCreateCustomerWhenDocumentNumberDoesNotExist() {
        when(accountRepository.findByDocumentNumber("12345678900")).thenReturn(Optional.empty());
        when(accountRepository.save(org.mockito.ArgumentMatchers.any(Account.class)))
                .thenAnswer(invocation -> {
                    Account account = invocation.getArgument(0);
                    if (account.getId() == null) {
                        account.setId(1L);
                    }
                    return account;
                });

        ReadAccountDto result = service.createAccount(" 12345678900 ");

        assertNotNull(result);
        assertEquals("12345678900", result.documentNumber());
        assertNotNull(result.accountId());
        verify(accountRepository).findByDocumentNumber("12345678900");
        verify(accountRepository).save(customerAccountCaptor.capture());
        assertEquals("12345678900", customerAccountCaptor.getValue().getDocumentNumber());
        assertNotNull(customerAccountCaptor.getValue().getId());
    }

    @Test
    void shouldReturnExistingCustomerWhenDocumentNumberAlreadyExists() {
        var existing = new Account();
        existing.setId(42L);
        existing.setDocumentNumber("12345678900");

        when(accountRepository.findByDocumentNumber("12345678900")).thenReturn(Optional.of(existing));

        ReadAccountDto result = service.createAccount("12345678900");

        assertNotNull(result);
        assertEquals("12345678900", result.documentNumber());
        assertEquals(42L, result.accountId());
        verify(accountRepository).findByDocumentNumber("12345678900");
        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldThrowAccountNotFoundExceptionWhenAccountDoesNotExist() {
        when(accountRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.getAccount(1L));

        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnAccountWhenAccountExists() {
        var existingAccount = new Account();
        existingAccount.setId(1L);
        existingAccount.setDocumentNumber("12345678900");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(existingAccount));

        ReadAccountDto result = service.getAccount(1L);

        assertNotNull(result);
        assertEquals(1L, result.accountId());
        assertEquals("12345678900", result.documentNumber());
        verify(accountRepository, times(1)).findById(1L);
    }
}