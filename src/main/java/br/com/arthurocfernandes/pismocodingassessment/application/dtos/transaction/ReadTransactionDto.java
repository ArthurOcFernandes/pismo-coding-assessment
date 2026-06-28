package br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction;

import br.com.arthurocfernandes.pismocodingassessment.domain.entities.Transaction;
import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(
        name = "TransactionResponse",
        description = "Represents a transaction"
)
public record ReadTransactionDto (
        @Schema(
                description = "Transaction identifier",
                example = "1"
        )
        Long transactionId,
        @Schema(
                description = "Account identifier",
                example = "1"
        )
        Long accountId,
        @Schema(
                description = "Operation type"
        )
        OperationType operationType,
        @Schema(
                description = "Amount used in operation",
                example = "1000.10"
        )
        BigDecimal amount) {
    public ReadTransactionDto(Transaction transaction){
        this(transaction.getTransactionId(), transaction.getAccount().getId(), transaction.getOperationType(), transaction.getAmount());
    }
}
