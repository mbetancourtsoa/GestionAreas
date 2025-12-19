package com.soaint.gestion.areas.api.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "UsuarioArea", schema = "dbo")
public class UsuarioAreaEntity {

    @EmbeddedId
    private UsuarioAreaId id;

    @ManyToOne
    @JoinColumn(name = "IdUsuario", insertable = false, updatable = false)
    private UsuariosEntity usuario;

    @ManyToOne
    @JoinColumn(name = "IdArea", insertable = false, updatable = false)
    private AreasEntity area;

    @Column(name = "RolArea")
    private String rolArea;

    @Column(name = "Activo")
    private Boolean activo;
}