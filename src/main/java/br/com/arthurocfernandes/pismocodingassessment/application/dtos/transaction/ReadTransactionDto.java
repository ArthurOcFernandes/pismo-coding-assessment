package br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Transaction;
import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;

import java.math.BigDecimal;

public record ReadTransactionDto (Long transactionId, Long accountId, OperationType operationType, BigDecimal amount) {
    public ReadTransactionDto(Transaction transaction){
        this(transaction.getTransactionId(), transaction.getAccount().getId(), transaction.getOperationType(), transaction.getAmount());
    }
}
