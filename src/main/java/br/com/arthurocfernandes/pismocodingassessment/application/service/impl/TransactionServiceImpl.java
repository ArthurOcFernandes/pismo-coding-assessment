package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.CreateTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.ReadTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.TransactionService;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Transaction;
import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.AccountNotFoundException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.InvalidOperationException;
import br.com.arthurocfernandes.pismocodingassessment.domain.exceptions.InvalidOperationTypeException;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public ReadTransactionDto CreateTransaction(CreateTransactionDto createTransactionDto) {
        var account = accountRepository.findById(createTransactionDto.accountId()).orElseThrow(AccountNotFoundException::new);
        var operation = OperationType.fromValue(createTransactionDto.operationTypeId()).orElseThrow(InvalidOperationTypeException::new);

        if (!operation.isValid(createTransactionDto.amount()))
            throw new InvalidOperationException();

        var transaction = Transaction.builder()
                .account(account)
                .operationType(operation)
                .amount(createTransactionDto.amount())
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        return new ReadTransactionDto(transactionRepository.save(transaction));
    }
}