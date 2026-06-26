package br.com.arthurocfernandes.pismocodingassessment.application.service;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadCustomerAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;

public interface CustomerAccountService {
    Result<ReadCustomerAccountDto> createCustomerByDocument(String documentNumber);
}

