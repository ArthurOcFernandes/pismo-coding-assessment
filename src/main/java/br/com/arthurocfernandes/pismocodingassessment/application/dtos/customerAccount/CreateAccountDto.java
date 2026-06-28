package br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(
        name = "CreateAccountRequest",
        description = "Request used to create a customer account."
)
public record CreateAccountDto(
        @NotBlank
        @Pattern(regexp = "^\\d{11}$")
        @Schema(
                description = "Customer document number. Must contain 11 digits.",
                example = "12345678910"
        )
        String documentNumber
) {
}
