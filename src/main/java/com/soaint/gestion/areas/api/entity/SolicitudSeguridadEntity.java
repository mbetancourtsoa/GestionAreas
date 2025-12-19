package com.soaint.gestion.areas.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "SolicitudSeguridad", schema = "dbo")
public class SolicitudSeguridadEntity {

    @Id
    @Column(name = "IdSolicitud", columnDefinition = "uniqueidentifier")
    private UUID idSolicitud;

    @Column(name = "AreaSolicitante")
    private String areaSolicitante;                // Combo del formulario

    @Column(name = "TipoSolicitud")
    private String tipoSolicitud;                  // "EXPEDIENTE" | "DOCUMENTO"

    @Column(name = "OficinaProductora")
    private String oficinaProductora;

    @Column(name = "SerieDocumental")
    private String serieDocumental;

    @Column(name = "SubSerieDocumental")
    private String subSerieDocumental;

    @Column(name = "NombreExpediente")
    private String nombreExpediente;

    @Column(name = "AccesoHasta")
    private LocalDateTime accesoHasta;             // Control de tiempo

    @Column(name = "Estado")
    private String estado;                         // PENDIENTE/APROBADA/RECHAZADA/ASIGNADA/CERRADA

    @Column(name = "UsuarioSolicitante")
    private String usuarioSolicitante;             // username del solicitante

    @Column(name = "FechaSolicitud")
    private LocalDateTime fechaSolicitud;

    @PrePersist
    public void onCreate() {
        if (idSolicitud == null) idSolicitud = UUID.randomUUID();
        if (fechaSolicitud == null) fechaSolicitud = LocalDateTime.now();
        if (estado == null) estado = "PENDIENTE";
    }
}
