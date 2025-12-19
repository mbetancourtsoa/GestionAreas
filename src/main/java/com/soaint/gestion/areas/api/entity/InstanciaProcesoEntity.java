package com.soaint.gestion.areas.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "InstanciaProceso", schema = "dbo")
public class InstanciaProcesoEntity {

    @Id
    @Column(name = "IdInstancia", columnDefinition = "uniqueidentifier")
    private UUID idInstancia;

    @Column(name = "IdSolicitud", columnDefinition = "uniqueidentifier")
    private UUID idSolicitud;            // FK -> SolicitudSeguridad.IdSolicitud

    @Column(name = "Estado")
    private String estado;               // PENDIENTE/APROBADA/RECHAZADA/ASIGNADA/CERRADA

    @Column(name = "UsuarioCreador")
    private String usuarioCreador;

    @Column(name = "FechaInicio")
    private LocalDateTime fechaInicio;

    @Column(name = "FechaFin")
    private LocalDateTime fechaFin;

    @PrePersist
    public void onCreate() {
        if (idInstancia == null) idInstancia = UUID.randomUUID();
        if (fechaInicio == null) fechaInicio = LocalDateTime.now();
        if (estado == null) estado = "PENDIENTE";
    }
}
