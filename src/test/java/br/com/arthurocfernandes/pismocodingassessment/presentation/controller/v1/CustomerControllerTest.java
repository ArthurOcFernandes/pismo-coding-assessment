package br.com.arthurocfernandes.pismocodingassessment.presentation.controller.v1;

import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customer.CreateCustomerAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.dtos.customer.ReadCustomerAccountDto;
import br.com.arthurocfernandes.pismocodingassessment.application.result.OperationError;
import br.com.arthurocfernandes.pismocodingassessment.application.result.Result;
import br.com.arthurocfernandes.pismocodingassessment.application.service.CustomerAccountService;
import br.com.arthurocfernandes.pismocodingassessment.presentation.dtos.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerControllerTest {

    @Test
    void shouldReturnCreatedWhenServiceSucceeds() {
        CustomerAccountService service = documentNumber -> Result.success(new ReadCustomerAccountDto(documentNumber, 1L));
        var controller = new CustomerController(service);

        ResponseEntity<?> response = controller.createCustomer(new CreateCustomerAccountDto("12345678900"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertInstanceOf(ReadCustomerAccountDto.class, response.getBody());
        assertEquals("12345678900", ((ReadCustomerAccountDto) response.getBody()).documentNumber());
    }

    @Test
    void shouldReturnErrorResponseWhenServiceFails() {
        CustomerAccountService service = documentNumber -> Result.failure(new OperationError(
                "DOCUMENT_NUMBER_REQUIRED",
                "documentNumber must be provided",
                HttpStatus.BAD_REQUEST
        ));
        var controller = new CustomerController(service);

        ResponseEntity<?> response = controller.createCustomer(new CreateCustomerAccountDto(""));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertInstanceOf(ApiErrorResponse.class, response.getBody());
        assertTrue(((ApiErrorResponse) response.getBody()).message().contains("documentNumber"));
    }
}

