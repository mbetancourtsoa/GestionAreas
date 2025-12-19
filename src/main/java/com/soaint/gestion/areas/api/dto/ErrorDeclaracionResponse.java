package com.soaint.gestion.areas.api.dto;

import java.time.LocalDateTime;

import com.soaint.gestion.areas.api.domain.ErrorDeclaracion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDeclaracionResponse {

	private Integer idError;   
	private String  tipoError;
    private String  mensajeError;
    private String  traceId;
    private LocalDateTime fechaError;

    public static ErrorDeclaracionResponse from(ErrorDeclaracion e) {
        return ErrorDeclaracionResponse.builder()
                .idError(e.getId())               
                .tipoError(e.getTipoError())
                .mensajeError(e.getMensajeError())
                .traceId(e.getTraceId())
                .fechaError(e.getFechaError())
                .build();
    }
}
