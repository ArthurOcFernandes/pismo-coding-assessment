package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.AccountService;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Account;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.AccountNotFoundException;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public ReadAccountDto createAccount(String documentNumber) {
        var normalizedDocumentNumber = documentNumber.trim();

        return accountRepository.findByDocumentNumber(normalizedDocumentNumber)
                .map(ReadAccountDto::new)
                .orElseGet(() -> {
                    Account account = new Account();
                    account.setDocumentNumber(normalizedDocumentNumber);
                    return new ReadAccountDto(accountRepository.save(account));
                });
    }

    @Override
    public ReadAccountDto getAccount(long accountId) {
        return accountRepository.findById(accountId)
                .map(ReadAccountDto::new)
                .orElseThrow(AccountNotFoundException::new);
    }
}