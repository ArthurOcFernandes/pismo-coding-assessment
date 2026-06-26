package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl service;

    @Captor
    private ArgumentCaptor<CustomerAccount> customerAccountCaptor;

    @Test
    void shouldReturnFailureWhenDocumentNumberIsMissing() {
        Result<ReadAccountDto> result = service.createAccount("   ");

        assertFalse(result.isSuccess());
        assertEquals("DOCUMENT_NUMBER_REQUIRED", result.getError().code());
        assertEquals("documentNumber must be provided", result.getError().message());
        verify(accountRepository, never()).findByDocumentNumber(any());
        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldCreateCustomerWhenDocumentNumberDoesNotExist() {
        when(accountRepository.findByDocumentNumber("12345678900")).thenReturn(java.util.Optional.empty());
        when(accountRepository.save(org.mockito.ArgumentMatchers.any(CustomerAccount.class)))
                .thenAnswer(invocation -> {
                    CustomerAccount account = invocation.getArgument(0);
                    if (account.getId() == null) {
                        account.setId(1L);
                    }
                    return account;
                });

        Result<ReadAccountDto> result = service.createAccount(" 12345678900 ");

        assertTrue(result.isSuccess());
        assertEquals("12345678900", result.getValue().documentNumber());
        assertNotNull(result.getValue().id());
        verify(accountRepository).findByDocumentNumber("12345678900");
        verify(accountRepository).save(customerAccountCaptor.capture());
        assertEquals("12345678900", customerAccountCaptor.getValue().getDocumentNumber());
        assertNotNull(customerAccountCaptor.getValue().getId());
    }

    @Test
    void shouldReturnExistingCustomerWhenDocumentNumberAlreadyExists() {
        var existing = new CustomerAccount();
        existing.setId(42L);
        existing.setDocumentNumber("12345678900");

        when(accountRepository.findByDocumentNumber("12345678900")).thenReturn(java.util.Optional.of(existing));

        Result<ReadAccountDto> result = service.createAccount("12345678900");

        assertTrue(result.isSuccess());
        assertEquals("12345678900", result.getValue().documentNumber());
        assertEquals(42L, result.getValue().id());
        verify(accountRepository).findByDocumentNumber("12345678900");
        verify(accountRepository, never()).save(any());
    }
}

