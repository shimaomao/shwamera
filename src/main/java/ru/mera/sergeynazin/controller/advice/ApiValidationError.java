package ru.mera.sergeynazin.controller.advice;

import java.util.Objects;

class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ApiValidationError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiValidationError)) return false;
        ApiValidationError that = (ApiValidationError) o;
        return Objects.equals(this.object, that.object) &&
            Objects.equals(this.field, that.field) &&
            Objects.equals(this.rejectedValue, that.rejectedValue) &&
            Objects.equals(this.message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.object, this.field, this.rejectedValue, this.message);
    }
}
