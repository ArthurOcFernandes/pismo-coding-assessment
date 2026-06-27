package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.CreateAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.ReadAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<ReadAccountDto> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        ReadAccountDto readAccountDto = accountService.createAccount(createAccountDto.documentNumber());

        return ResponseEntity.status(HttpStatus.CREATED).body(readAccountDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<ReadAccountDto> getAccount(@PathVariable long id){
        ReadAccountDto readAccountDto = accountService.getAccount(id);

        return ResponseEntity.status(HttpStatus.OK).body(readAccountDto);
    }
}