package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customer.ReadCustomerAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.CustomerAccountService;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;
import br.com.arthurocfernandes.pismocodingassessment.application.result.OperationError;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerAccountAccountServiceImpl implements CustomerAccountService {

    private final CustomerRepository customerRepository;

    public CustomerAccountAccountServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Result<ReadCustomerAccountDto> createCustomerByDocument(String documentNumber) {
        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            return Result.failure(new OperationError(
                    "DOCUMENT_NUMBER_REQUIRED",
                    "documentNumber must be provided",
                    HttpStatus.UNPROCESSABLE_CONTENT
            ));
        }

        var normalizedDocumentNumber = documentNumber.trim();

        return customerRepository.findByDocumentNumber(normalizedDocumentNumber)
                .map(existing -> Result.success(new ReadCustomerAccountDto(existing)))
                .orElseGet(() -> {
                    CustomerAccount account = new CustomerAccount();
                    account.setDocumentNumber(normalizedDocumentNumber);

                    return Result.success(new ReadCustomerAccountDto(customerRepository.save(account)));
                });
    }
}


