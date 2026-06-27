package br.com.arthurocfernandes.pismocodingassessment.domain.enums;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

public enum OperationType {

    PURCHASE(1) {
        @Override
        public boolean isValid(BigDecimal amount) {
            return amount.signum() < 0;
        }
    },

    INSTALLMENT_PURCHASE(2) {
        @Override
        public boolean isValid(BigDecimal amount) {
            return amount.signum() < 0;
        }
    },

    WITHDRAWAL(3) {
        @Override
        public boolean isValid(BigDecimal amount) {
            return amount.signum() < 0;
        }
    },

    PAYMENT(4) {
        @Override
        public boolean isValid(BigDecimal amount) {
            return amount.signum() > 0;
        }
    };

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

    public abstract boolean isValid(BigDecimal amount);
}
