package br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(
        name = "CreateTransactionRequest",
        description = "Request used to create a transaction"
)
public record CreateTransactionDto(
        @Schema(
                description = "Account identifier",
                example = "1"
        )
        @NotNull Long accountId,
        @Schema(
                description = """
                        Operation type.
                        Possible values:
                        1 - PURCHASE
                        2 - INSTALLMENT_PURCHASE
                        3 - WITHDRAWAL
                        4 - PAYMENT
                        """,
                example = "1"
        )
        @NotNull Integer operationTypeId,
        @Schema(
                description = "Amount used in operation",
                example = "1000.10"
        )
        @NotNull BigDecimal amount
) {
}