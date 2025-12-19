package com.soaint.gestion.areas.api.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
//@Table(name = "EstadoDeclaracion", schema = "dbo", catalog = "ColpensionesBusinessCoreINT")
@Table(name = "EstadoDeclaracion")
@Data @NoArgsConstructor @AllArgsConstructor
public class EstadoDeclaracion {
    @Id
    @Column(name = "IdEstado")
    private Integer id;

    @Column(name = "Codigo", length = 20, nullable = false)
    private String codigo;

    @Column(name = "Descripcion", length = 100)
    private String descripcion;
}