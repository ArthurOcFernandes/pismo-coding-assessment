package br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CreateAccountDto(String documentNumber){}
