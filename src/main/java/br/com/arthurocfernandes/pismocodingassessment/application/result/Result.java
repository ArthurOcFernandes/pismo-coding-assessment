package br.com.arthurocfernandes.pismocodingassessment.application.result;

import java.util.Objects;

public sealed interface Result<T> permits Result.Success, Result.Failure {
    boolean isSuccess();

    T getValue();

    OperationError getError();

    static <T> Result<T> success(T value) {
        return new Success<>(Objects.requireNonNull(value, "value must be provided"));
    }

    static <T> Result<T> failure(OperationError error) {
        return new Failure<>(Objects.requireNonNull(error, "error must be provided"));
    }

    record Success<T>(T value) implements Result<T> {
        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public OperationError getError() {
            return null;
        }
    }

    record Failure<T>(OperationError error) implements Result<T> {
        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public T getValue() {
            throw new IllegalStateException("Cannot access the value of a failed result");
        }

        @Override
        public OperationError getError() {
            return error;
        }
    }
}

