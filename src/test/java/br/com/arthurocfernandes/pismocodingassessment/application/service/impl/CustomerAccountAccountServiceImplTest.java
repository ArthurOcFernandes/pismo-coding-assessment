package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customer.ReadCustomerAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerAccountAccountServiceImplTest {

    @Test
    void shouldReturnFailureWhenDocumentNumberIsMissing() {
        var service = new CustomerAccountAccountServiceImpl(repository(Optional.empty(), new AtomicReference<>()));

        Result<ReadCustomerAccountDto> result = service.createCustomerByDocument("   ");

        assertFalse(result.isSuccess());
        assertEquals("DOCUMENT_NUMBER_REQUIRED", result.getError().code());
        assertEquals("documentNumber must be provided", result.getError().message());
    }

    @Test
    void shouldCreateCustomerWhenDocumentNumberDoesNotExist() {
        var savedCustomer = new AtomicReference<CustomerAccount>();
        var service = new CustomerAccountAccountServiceImpl(repository(Optional.empty(), savedCustomer));

        Result<ReadCustomerAccountDto> result = service.createCustomerByDocument(" 12345678900 ");

        assertTrue(result.isSuccess());
        assertEquals("12345678900", result.getValue().documentNumber());
        assertNotNull(result.getValue().id());
        assertEquals("12345678900", savedCustomer.get().getDocumentNumber());
    }

    @Test
    void shouldReturnExistingCustomerWhenDocumentNumberAlreadyExists() {
        var existing = new CustomerAccount();
        existing.setId(42L);
        existing.setDocumentNumber("12345678900");

        var service = new CustomerAccountAccountServiceImpl(repository(Optional.of(existing), new AtomicReference<>()));

        Result<ReadCustomerAccountDto> result = service.createCustomerByDocument("12345678900");

        assertTrue(result.isSuccess());
        assertEquals("12345678900", result.getValue().documentNumber());
        assertEquals(42L, result.getValue().id());
    }

    private static CustomerRepository repository(Optional<CustomerAccount> existingCustomer,
                                                 AtomicReference<CustomerAccount> savedCustomer) {
        return (CustomerRepository) Proxy.newProxyInstance(
                CustomerRepository.class.getClassLoader(),
                new Class<?>[]{CustomerRepository.class},
                (proxy, method, args) -> switch (method.getName()) {
                    case "findByDocumentNumber" -> existingCustomer;
                    case "save" -> {
                        CustomerAccount account = (CustomerAccount) args[0];
                        if (account.getId() == null) {
                            account.setId(1L);
                        }
                        savedCustomer.set(account);
                        yield account;
                    }
                    case "toString" -> "CustomerRepositoryTestProxy";
                    case "hashCode" -> System.identityHashCode(proxy);
                    case "equals" -> proxy == args[0];
                    default -> throw new UnsupportedOperationException("Unexpected method: " + method.getName());
                }
        );
    }
}

