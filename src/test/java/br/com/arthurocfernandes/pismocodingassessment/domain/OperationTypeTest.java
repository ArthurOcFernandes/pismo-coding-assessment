package br.com.arthurocfernandes.pismocodingassessment.domain;

import br.com.arthurocfernandes.pismocodingassessment.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationTypeTest {
    @ParameterizedTest
    @EnumSource(
            value = OperationType.class,
            names = {
                    "PURCHASE",
                    "INSTALLMENT_PURCHASE",
                    "WITHDRAWAL"
            }
    )
    void shouldApplyNegativeSignalForDebitOperations(OperationType operationType) {
        BigDecimal result = operationType.applySignal(new BigDecimal("1000"));

        assertEquals(new BigDecimal("-1000"), result);
    }

    @Test
    void shouldKeepPositiveSignalForPayment() {
        BigDecimal result = OperationType.PAYMENT.applySignal(new BigDecimal("1000"));

        assertEquals(new BigDecimal("1000"), result);
    }
}
