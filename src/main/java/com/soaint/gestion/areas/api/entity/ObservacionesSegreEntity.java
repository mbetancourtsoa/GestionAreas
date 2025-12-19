package com.soaint.gestion.areas.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "ObservacionesSegre", schema = "dbo")
public class ObservacionesSegreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdSolicitud", columnDefinition = "uniqueidentifier")
    private UUID idSolicitud;             // FK -> SolicitudSeguridad.IdSolicitud

    @Column(name = "Username")
    private String username;              // autor de la observación

    @Column(name = "Observacion", columnDefinition = "varchar(max)")
    private String observacion;

    @Column(name = "FechaObservacion")
    private LocalDateTime fechaObservacion;

    @PrePersist
    public void onCreate() {
        if (fechaObservacion == null) fechaObservacion = LocalDateTime.now();
    }
}
