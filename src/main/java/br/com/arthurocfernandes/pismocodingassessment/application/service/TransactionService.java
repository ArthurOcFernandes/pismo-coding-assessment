package br.com.arthurocfernandes.pismocodingassessment.application.service;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.ReadTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;

import java.math.BigDecimal;

public interface TransactionService {
    Result<ReadTransactionDto> CreateTransaction(Long accountId, int operationType, BigDecimal amount);
}
