package com.soaint.gestion.areas.api.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "SolicitudObjetoDetalle", schema = "dbo")
public class SolicitudObjetoDetalleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "IdSolicitud")
    private UUID idSolicitud;

    @Column(name = "TipoObjeto", length = 50)
    private String tipoObjeto;

    // =================================================================================
    // CORRECCIÓN CLAVE:
    // Mantenemos el nombre de variable 'idObjeto' para que tu Service compile (setIdObjeto).
    // Pero en @Column ponemos "IdentificadorObjeto" que es lo que tienes en tu SQL.
    // =================================================================================
    @Column(name = "IdentificadorObjeto", length = 255)
    private String idObjeto; 

    // BORRA o COMENTA el campo 'private String identificadorObjeto;' 
    // porque ya estamos mapeando esa columna arriba y si lo dejas duplicado dará error.
    
    // private String identificadorObjeto; <--- ELIMINAR ESTO

    @Column(name = "NombreObjeto", length = 500)
    private String nombreObjeto;

    @Column(name = "Clase", length = 50)
    private String clase;

    @Column(name = "Sensibilidad", length = 50)
    private String sensibilidad;

    @Column(name = "Observacion", columnDefinition = "varchar(max)")
    private String observacion;
}