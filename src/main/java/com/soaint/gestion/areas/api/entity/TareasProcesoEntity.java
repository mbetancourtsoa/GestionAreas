package com.soaint.gestion.areas.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TareasProceso", schema = "dbo")
public class TareasProcesoEntity {

    @Id
    @Column(name = "IdTarea", columnDefinition = "uniqueidentifier")
    private UUID idTarea;

    @Column(name = "IdInstancia", columnDefinition = "uniqueidentifier")
    private UUID idInstancia;           // FK -> InstanciaProceso.IdInstancia

    @Column(name = "NombreTarea")
    private String nombreTarea;         // "APROBAR_RECHAZAR", "ASIGNAR_ACCESOS", "CONTROL_TIEMPO", "QUITAR_ACCESOS"

    @Column(name = "Estado")
    private String estado;              // PENDIENTE/EN_PROCESO/FINALIZADA/ERROR

    @Column(name = "Responsable")
    private String responsable;         // username (asignador/robot)

    @Column(name = "FechaInicio")
    private LocalDateTime fechaInicio;

    @Column(name = "FechaFin")
    private LocalDateTime fechaFin;

    @Column(name = "OrdenEjecucion")
    private Integer ordenEjecucion;

    @PrePersist
    public void onCreate() {
        if (idTarea == null) idTarea = UUID.randomUUID();
        if (fechaInicio == null) fechaInicio = LocalDateTime.now();
        if (estado == null) estado = "PENDIENTE";
    }
}
