package br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.CustomerAccount;

public record ReadAccountDto(String documentNumber, Long id) {
    public ReadAccountDto(CustomerAccount customerAccount) {
        this(customerAccount.getDocumentNumber(), customerAccount.getId());
    }
}

