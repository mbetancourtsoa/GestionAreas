package com.soaint.gestion.areas.api.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ErrorDTO {
    private String code;          // SCCLI-xxxxx
    private String devMessage;    // mensaje técnico
    private String userMessage;   // mensaje amigable
    private String exceptionType; // NullPointerException, …
    private String traceID;       // UUID
}