package com.soaint.gestion.areas.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorDeclaracionDto {
    private Integer idError;
    private Integer idDeclaracion;
    private String tipoError;
    private String mensajeError;
    private LocalDateTime fechaError;
}

