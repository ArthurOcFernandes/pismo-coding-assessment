package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.AccountService;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;
import br.com.arthurocfernandes.pismocodingassessment.application.result.OperationError;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Result<ReadAccountDto> createAccount(String documentNumber) {
        if (documentNumber == null || documentNumber.trim().isEmpty()) {
            return Result.failure(new OperationError(
                    "DOCUMENT_NUMBER_REQUIRED",
                    "documentNumber must be provided",
                    HttpStatus.UNPROCESSABLE_CONTENT
            ));
        }

        var normalizedDocumentNumber = documentNumber.trim();

        return accountRepository.findByDocumentNumber(normalizedDocumentNumber)
                .map(existing -> Result.success(new ReadAccountDto(existing)))
                .orElseGet(() -> {
                    CustomerAccount account = new CustomerAccount();
                    account.setDocumentNumber(normalizedDocumentNumber);

                    return Result.success(new ReadAccountDto(accountRepository.save(account)));
                });
    }

    @Override
    public Result<ReadAccountDto> getAccount(long accountId) {
        var account = accountRepository.findById(accountId);

        return account
                .map(customerAccount -> Result.success(new ReadAccountDto(customerAccount)))
                .orElseGet(() -> Result.failure(
                        new OperationError(
                                "RESOURCE_NOT_FOUND",
                                String.format("Account with id %d not found", accountId),
                                HttpStatus.NOT_FOUND
                        )));
    }
}