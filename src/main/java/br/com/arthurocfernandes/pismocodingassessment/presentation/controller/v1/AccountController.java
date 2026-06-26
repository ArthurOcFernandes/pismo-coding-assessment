package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customerAccount.CreateAccountDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.arthurocfernandes.pismocodingassessment.application.service.AccountService;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        var result = accountService.createAccount(createAccountDto.documentNumber());

        if (!result.isSuccess()) {
            var error = result.getError();
            return ResponseEntity.status(error.status()).body(error);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getValue());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getAccount(@PathVariable long id){
        var result = accountService.getAccount(id);

        if (!result.isSuccess()) {
            var error = result.getError();
            return ResponseEntity.status(error.status()).body(error);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result.getValue());
    }

}
