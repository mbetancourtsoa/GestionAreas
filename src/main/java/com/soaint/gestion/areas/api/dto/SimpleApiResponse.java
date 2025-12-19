package com.soaint.gestion.areas.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleApiResponse<T> {
    private T body;              // payload
    private String businessStatus; // "200" o el que corresponda
    private String timeResponse; // "yyyy-MM-dd HH:mm:ss"
    private String message;      // texto amigable
}
