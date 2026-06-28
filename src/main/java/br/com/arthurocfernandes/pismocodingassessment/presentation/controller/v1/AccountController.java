package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.CreateAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.AccountService;
import br.com.arthurocfernandes.pismocodingassessment.presentation.exceptionhandler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    @Operation(summary = "Create an account", description = "Creates an account from a provided document number.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created", content = @Content(schema = @Schema(implementation = ReadAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ReadAccountDto> createAccount(@Valid @RequestBody CreateAccountDto createAccountDto) {
        ReadAccountDto readAccountDto = accountService.createAccount(createAccountDto.documentNumber());

        return ResponseEntity.status(HttpStatus.CREATED).body(readAccountDto);
    }

    @Operation(summary = "Get an account by id", description = "Returns an account given its identifier.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account found", content = @Content(schema = @Schema(implementation = ReadAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "Account not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<ReadAccountDto> getAccount(@PathVariable long id) {
        ReadAccountDto readAccountDto = accountService.getAccount(id);

        return ResponseEntity.status(HttpStatus.OK).body(readAccountDto);
    }
}