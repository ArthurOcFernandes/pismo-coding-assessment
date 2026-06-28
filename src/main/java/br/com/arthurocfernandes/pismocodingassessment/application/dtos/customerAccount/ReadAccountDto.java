package br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Account;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(
        name = "AccountResponse",
        description = "Represents a customer account."
)
public record ReadAccountDto(
        @Schema(description = "Customer document number.", example = "12345678910")
        String documentNumber,
        @Schema(description = "Unique account identifier.", example = "1")
        Long accountId
) {
    public ReadAccountDto(Account account) {
        this(account.getDocumentNumber(), account.getId());
    }
}

