package br.com.arthurocfernandes.pismocodingassessment.domain.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Getter
public enum OperationType {

    PURCHASE(1, -1),
    INSTALLMENT_PURCHASE(2, -1),
    WITHDRAWAL(3, -1),
    PAYMENT(4, 1);

    private final int value;
    private final int signal;

    OperationType(int value, int signal) {
        this.value = value;
        this.signal = signal;
    }

    public static Optional<OperationType> fromValue(int value) {
        return Arrays.stream(values())
                .filter(operationType -> operationType.value == value)
                .findFirst();
    }

    public BigDecimal applySignal(BigDecimal amount) {
        return amount.multiply(BigDecimal.valueOf(signal));
    }
}