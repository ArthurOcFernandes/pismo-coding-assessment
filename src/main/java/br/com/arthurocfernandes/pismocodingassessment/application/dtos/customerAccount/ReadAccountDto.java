package br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Account;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record ReadAccountDto(String documentNumber, Long accountId) {
    public ReadAccountDto(Account account) {
        this(account.getDocumentNumber(), account.getId());
    }
}

