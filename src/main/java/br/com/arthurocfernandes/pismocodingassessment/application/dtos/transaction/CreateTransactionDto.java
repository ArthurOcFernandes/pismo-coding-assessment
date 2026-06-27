package br.com.arthurocfernandes.pismocodingassessment.application.dtos.transaction;

import java.math.BigDecimal;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateTransactionDto(Long accountId, Integer operationTypeId, BigDecimal amount) {
}