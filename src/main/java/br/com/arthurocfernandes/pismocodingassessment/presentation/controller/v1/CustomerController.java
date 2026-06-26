package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customer.CreateCustomerAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.presentation.dtos.ApiErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arthurocfernandes.pismocodingassessment.application.service.CustomerAccountService;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers")
public class CustomerController {

    private final CustomerAccountService customerAccountService;

    public CustomerController(CustomerAccountService customerAccountService) {
        this.customerAccountService = customerAccountService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerAccountDto createCustomerAccountDto) {
        var result = customerAccountService.createCustomerByDocument(createCustomerAccountDto.documentNumber());

        if (!result.isSuccess()) {
            var error = result.getError();
            return ResponseEntity.status(error.status()).body(new ApiErrorResponse(error.code(), error.message()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getValue());
    }
}
