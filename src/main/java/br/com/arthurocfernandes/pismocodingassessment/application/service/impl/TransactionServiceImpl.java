package br.com.arthurocfernandes.pismocodingassessment.application.service.impl;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.ReadTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.OperationError;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.application.service.TransactionService;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;
import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Transaction;
import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.AccountRepository;
import br.com.arthurocfernandes.pismocodingassessment.infrastructure.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Result<ReadTransactionDto> CreateTransaction(Long accountId, int operationType, BigDecimal amount) {
        var account = accountRepository.findById(accountId);

        if (account.isEmpty()) {
            return Result.failure(new OperationError(
                    "ACCOUNT_NOT_FOUND",
                    "A valid account must be provided",
                    HttpStatus.UNPROCESSABLE_CONTENT
            ));
        }

        var operation = OperationType.fromValue(operationType);

        if (operation.isEmpty()) {
            return Result.failure(new OperationError(
                    "INVALID_OPERATION_TYPE",
                    "A valid operation must be provided",
                    HttpStatus.UNPROCESSABLE_CONTENT
            ));
        }

        var transaction = Transaction.builder()
                .transactionId(null)
                .account(
                        CustomerAccount.builder()
                                .id(account.get().getId())
                                .documentNumber(account.get().getDocumentNumber())
                                .createdAt(account.get().getCreatedAt())
                                .build())
                .operationType(operation.get())
                .amount(amount)
                .createdAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        return Result.success(new ReadTransactionDto(transactionRepository.save(transaction)));
    }
}
