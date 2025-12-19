package com.soaint.gestion.areas.api.exception;

public class ValidationException extends AppException {
    public ValidationException(String code, String message) {
        super(code, message);
    }
}