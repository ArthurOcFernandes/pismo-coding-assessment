package br.com.arthurocfernandes.pismocodingassessment.domain.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum OperationType {
    PURCHASE(1), INSTALLMENT_PURCHASE(2), WITHDRAWAL(3), PAYMENT(4);

    @Getter
    private final int value;

    OperationType(int value) {
        this.value = value;
    }


    public static Optional<OperationType> fromValue(int value) {
        return Arrays.stream(values())
                .filter(operationType -> operationType.value == value)
                .findFirst();
    }
}
