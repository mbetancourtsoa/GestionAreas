package com.soaint.gestion.areas.api.dto;

import java.time.LocalDateTime;

import com.soaint.gestion.areas.api.domain.Declaracion;

import lombok.Data;

@Data
public class DeclaracionResponse {

    private Integer idDeclaracion;
    private String idDocumento;
    private String nombreDocumento;
    private String estado;              // “PENDIENTE”, “EN_PROCESO”, …
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaEjecucion;

    /* ---------- factory ---------- */
    public static DeclaracionResponse from(Declaracion d) {
        DeclaracionResponse r = new DeclaracionResponse();
        r.setIdDeclaracion(d.getId());
        r.setIdDocumento(d.getIdDocumento());
        r.setNombreDocumento(d.getNombreDocumento());
        r.setEstado(d.getEstado().getCodigo());        // usa el código legible
        r.setFechaCreacion(d.getFechaCreacion());
        r.setFechaEjecucion(d.getFechaEjecucion());
        return r;
    }
}
