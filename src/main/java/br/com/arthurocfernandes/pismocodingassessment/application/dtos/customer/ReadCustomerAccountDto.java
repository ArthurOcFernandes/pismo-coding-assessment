package br.com.arthurocfernandes.pismocodingassessment.application.dtos.customer;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;

public record ReadCustomerAccountDto(String documentNumber, Long id) {
    public ReadCustomerAccountDto(CustomerAccount customerAccount) {
        this(customerAccount.getDocumentNumber(), customerAccount.getId());
    }
}

