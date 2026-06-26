package br.com.arthurocfernandes.pismocodingassessment.application.service;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;

public interface AccountService {
    Result<ReadAccountDto> createAccount(String documentNumber);
    Result<ReadAccountDto> getAccount(long accountId);
}

