package com.soaint.gestion.areas.api.exception;

public class NotFoundException extends AppException {
    public NotFoundException(String code, String message) {
        super(code, message);
    }
}