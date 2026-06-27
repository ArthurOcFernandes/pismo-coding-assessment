package br.com.arthurocfernandes.pismocodingassessment.application.service;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;

public interface AccountService {
    ReadAccountDto createAccount(String documentNumber);
    ReadAccountDto getAccount(long accountId);
}

