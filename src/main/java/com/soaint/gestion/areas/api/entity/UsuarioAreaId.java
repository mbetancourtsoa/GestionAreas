package com.soaint.gestion.areas.api.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioAreaId implements Serializable {

    @Column(name = "IdUsuario")
    private UUID idUsuario;

    @Column(name = "IdArea")
    private UUID idArea;
}
