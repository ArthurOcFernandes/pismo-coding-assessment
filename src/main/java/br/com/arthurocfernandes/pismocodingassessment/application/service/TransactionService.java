package br.com.arthurocfernandes.pismocodingassessment.application.service;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.CreateTransactionDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction.ReadTransactionDto;

public interface TransactionService {
    ReadTransactionDto CreateTransaction(CreateTransactionDto createTransactionDto);
}