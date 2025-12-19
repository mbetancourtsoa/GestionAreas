package com.soaint.gestion.areas.api.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class SolicitudSeguridadDto {
    private UUID idSolicitud;
    private String areaSolicitante;
    private String tipoSolicitud;
    private String oficinaProductora;
    private String serieDocumental;
    private String subSerieDocumental;
    private String nombreExpediente;
    private LocalDateTime accesoHasta;
    private String estado;
    private String usuarioSolicitante;
    private LocalDateTime fechaSolicitud;

    private List<SolicitudObjetoDetalleDto> objetos;
    private List<SolicitudUsuarioDetalleDto> usuarios;
    private List<ObservacionDto> observaciones;
}
