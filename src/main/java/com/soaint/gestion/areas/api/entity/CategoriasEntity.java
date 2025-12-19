package com.soaint.gestion.areas.api.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Categorias", schema = "dbo")
public class CategoriasEntity {

    @Id
    @Column(name = "idCategoria", columnDefinition = "uniqueidentifier")
    private UUID idCategoria;

    @Column(name = "IdArea", columnDefinition = "uniqueidentifier")
    private UUID idArea;

    @Column(name = "NombreCategoria", length = 255)
    private String nombreCategoria;

    @Column(name = "ClaseExpediente", length = 255)
    private String claseExpediente;

    @Column(name = "ClaseDocumento", length = 255)   // (deja el nombre tal cual en BD)
    private String claseDocumento;

    /* ← NUEVO CAMPO */
    @Column(name = "Fondo", length = 255)
    private String fondo;
    
    @Column(name = "SeguridadSegregada")
    private Integer seguridadSegregada;
}
