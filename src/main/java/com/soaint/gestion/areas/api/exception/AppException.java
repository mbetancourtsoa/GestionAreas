package com.soaint.gestion.areas.api.exception;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {
    private final String errorCode;
    public AppException(String errorCode, String message) { super(message); this.errorCode = errorCode; }
}

//public class ValidationException   extends AppException { public ValidationException(String c,String m){super(c,m);} }
//public class BusinessException     extends AppException { public BusinessException  (String c,String m){super(c,m);} }
//public class NotFoundException     extends AppException { public NotFoundException  (String c,String m){super(c,m);} }