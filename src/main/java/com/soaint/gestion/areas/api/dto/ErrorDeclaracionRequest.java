package com.soaint.gestion.areas.api.dto;

import lombok.Data;

@Data
public class ErrorDeclaracionRequest {
    private Integer idDeclaracion;
    private String tipoError;         // ej. "ERR-DEC-VALIDACION"
    private String mensajeError;
    private String traceId;
}